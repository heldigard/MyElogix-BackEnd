package com.elogix.api.customers.infrastructure.helpers.customer;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.CityBasic;
import com.elogix.api.customers.domain.model.ContactPersonBasic;
import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.DeliveryZoneBasic;
import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.Membership;
import com.elogix.api.customers.domain.model.Neighborhood;
import com.elogix.api.customers.domain.usecase.CityBasicUseCase;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.customers.domain.usecase.DeliveryZoneBasicUseCase;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.customers.domain.usecase.MembershipUseCase;
import com.elogix.api.customers.domain.usecase.NeighborhoodUseCase;
import com.elogix.api.customers.infrastructure.entry_points.dto.CustomerExcelResponse;
import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomerImportExcelHelper extends GenericImportExcelHelper<Customer, CustomerExcelResponse> {
    private final CustomerUseCase useCase;
    private final DocumentTypeUseCase docTypeUseCase;
    private final NeighborhoodUseCase neighborUseCase;
    private final CityBasicUseCase cityUseCase;
    private final DeliveryZoneBasicUseCase zoneUseCase;
    private final MembershipUseCase membershipUseCase;

    private Map<String, DocumentType> mappedDocumentTypes;
    private Map<String, Neighborhood> mappedNeighborhoods;
    private Map<String, CityBasic> mappedCities;
    private Map<String, DeliveryZoneBasic> mappedZones;
    private Map<String, Membership> mappedMemberships;

    public CustomerImportExcelHelper(
            ExcelHelper excelHelper,
            UpdateUtils updateUtils,
            CustomerUseCase useCase,
            DocumentTypeUseCase docTypeUseCase,
            NeighborhoodUseCase neighborUseCase,
            CityBasicUseCase cityUseCase,
            DeliveryZoneBasicUseCase zoneUseCase,
            MembershipUseCase membershipUseCase) {
        super(excelHelper, updateUtils);
        this.useCase = useCase;
        this.docTypeUseCase = docTypeUseCase;
        this.neighborUseCase = neighborUseCase;
        this.cityUseCase = cityUseCase;
        this.zoneUseCase = zoneUseCase;
        this.membershipUseCase = membershipUseCase;
    }

    private void initializeMaps() {
        try {
            log.info("Initializing maps for CustomerImportExcelHelper");

            List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(
                    List.of("name"),
                    List.of("asc"));

            // Initialize document types
            List<DocumentType> documentTypes = docTypeUseCase.findAll(sortOrders, false);
            this.mappedDocumentTypes = excelHelper.mapEntities(documentTypes, DocumentType::getName);

            // Initialize neighborhoods
            List<Neighborhood> neighborhoods = neighborUseCase.findAll(sortOrders, false);
            this.mappedNeighborhoods = excelHelper.mapEntities(neighborhoods, Neighborhood::getName);

            // Initialize cities
            List<CityBasic> cities = cityUseCase.findAll(sortOrders, false);
            this.mappedCities = excelHelper.mapEntities(cities, CityBasic::getName);

            // Initialize zones
            List<DeliveryZoneBasic> zones = zoneUseCase.findAll(sortOrders, false);
            this.mappedZones = excelHelper.mapEntities(zones, DeliveryZoneBasic::getName);

            // Initialize memberships
            List<Membership> memberships = membershipUseCase.findAll(sortOrders, false);
            this.mappedMemberships = excelHelper.mapEntities(memberships,
                    data -> data.getName().toString());

            log.info("Maps initialized successfully");
        } catch (Exception e) {
            log.error("Error initializing maps: ", e);
            throw new RuntimeException("Error initializing CustomerImportExcelHelper", e);
        }
    }

    @Override
    protected List<Customer> findAllEntities() {
        initializeMaps();
        return useCase.findAll(getSortOrders(), false);
    }

    @Override
    protected String getEntityIdentifier(Customer entity) {
        return entity.getDocumentNumber();
    }

    @Override
    protected Set<String> getRequiredColumns() {
        return Set.of("Documento", "Nombre", "Direccion", "Email", "Telefono");
    }

    @Override
    protected boolean isIdentifierColumn(String columnName) {
        return "Documento".equals(columnName);
    }

    @Override
    protected Customer processCell(
            int cellIdx,
            Cell cell,
            Customer customer,
            CustomerExcelResponse response,
            Map<Integer, String> mapColumns) {

        if (cell == null) {
            return customer;
        }

        final String cellValue = excelHelper.getCleanCellValue(cell, true);
        String columnName = mapColumns.get(cellIdx);

        if ((cellValue.isEmpty() && !columnName.equals("Ciudad"))
                || cellValue.contains("N/A")
                || cellValue.equals("0")) {
            return customer;
        }

        switch (columnName) {
            case "Documento":
                processDocument(customer, cellValue, response);
                break;

            case "Tipo":
                customer.setDocumentType(mappedDocumentTypes.get(cellValue));
                break;

            case "Nombre":
                customer.setName(cellValue);
                break;

            case "Direccion":
                setBranchOffice(customer, clearAddress(cellValue));
                break;

            case "Email":
                processEmail(customer, cellValue, response);
                break;

            case "Telefono":
                processPhone(customer, cellValue, response);
                break;

            case "Contacto":
                setContactPerson(customer, cellValue);
                break;

            case "Celular":
                processMobilePhone(customer, cellValue, response, true);
                break;

            case "Celular2":
                processMobilePhone(customer, cellValue, response, false);
                break;

            case "Ciudad":
                setCity(customer, cellValue);
                break;

            case "Sector":
                setNeighborhood(customer, cellValue);
                break;

            case "Ruta":
                setZone(customer, cellValue);
                break;

            case "Membresia":
                String membresia = cellValue.replace("(", "").replace(")", "").trim();
                customer.setMembership(mappedMemberships.get(membresia));
                break;
        }

        return customer;
    }

    private void processDocument(Customer customer, String value, CustomerExcelResponse response) {
        if (customer.getDocumentNumber() == null) {
            if (value.length() > 6 && value.length() < 15) {
                customer.setDocumentNumber(value);
            } else {
                response.addError("Error: " + value + " El documento debe tener entre 6 y 10 digitos");
            }
        }
    }

    private void processEmail(Customer customer, String value, CustomerExcelResponse response) {
        String email = excelHelper.getCleanEmailAddress(value);
        if (EmailValidator.getInstance().isValid(email)) {
            customer.setEmail(email);
        } else {
            response.addError("Error: " + email + " El email es invalido, customerId: " +
                    customer.getDocumentNumber());
        }
    }

    private void processPhone(Customer customer, String value, CustomerExcelResponse response) {
        String phone = clearPhone(value);
        if (phone.length() > 6 && phone.length() < 15) {
            customer.setPhone(phone);
        } else {
            response.addError("Error: " + phone + " El telefono debe tener entre 7 y 10 digitos, customerId: " +
                    customer.getDocumentNumber());
        }
    }

    private void processMobilePhone(Customer customer, String value, CustomerExcelResponse response,
            boolean isPrimary) {
        String phone = clearPhone(value);
        if (phone.length() > 6 && phone.length() < 15) {
            if (isPrimary) {
                setContactPhone(customer, phone);
            } else {
                setContactPhone2(customer, phone);
            }
        } else {
            response.addError("Error: " + phone + " El celular debe tener 10 digitos, customerId: " +
                    customer.getDocumentNumber());
        }
    }

    private void setBranchOffice(Customer customer, String cellValue) {
        if (customer.getBranchOfficeList() == null) {
            customer.setBranchOfficeList(new HashSet<>());
        }

        if (customer.getBranchOfficeList().isEmpty()) {
            customer.getBranchOfficeList().add(new BranchOffice());
        }

        BranchOffice office = customer.getBranchOfficeList().iterator().next();
        office.setCustomerId(customer.getId());
        office.setAddress(cellValue);
    }

    private String clearAddress(String address) {
        return address
                .replace(" NO ", "")
                .replace("#", "");
    }

    private void setContactPerson(Customer customer, String cellValue) {
        if (customer.getBranchOfficeList().isEmpty()) {
            customer.getBranchOfficeList().add(new BranchOffice());
        }

        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(office -> office
                        .setContactPerson(ContactPersonBasic.builder().name(cellValue).build()));
    }

    private void setContactPhone(Customer customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(office -> {
                    if (office.getContactPerson() != null) {
                        office.getContactPerson().setMobileNumberPrimary(cellValue);
                    }
                });
    }

    private void setContactPhone2(Customer customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(office -> {
                    if (office.getContactPerson() != null) {
                        office.getContactPerson().setMobileNumberSecondary(cellValue);
                    }
                });
    }

    private String clearPhone(String mobileNumber) {
        return mobileNumber
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace(".", "");
    }

    private void setCity(Customer customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(office -> {
                    if (mappedCities.get(cellValue) != null) {
                        office.setCity(mappedCities.get(cellValue));
                    } else if (mappedNeighborhoods.get(cellValue) != null) {
                        office.setCity(mappedNeighborhoods.get(cellValue).getCity());
                    }
                });
    }

    private void setNeighborhood(Customer customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(office -> {
                    if (mappedNeighborhoods.get(cellValue) != null) {
                        office.setNeighborhood(mappedNeighborhoods.get(cellValue));
                        if (office.getDeliveryZone() == null) {
                            office.setDeliveryZone(mappedNeighborhoods.get(cellValue).getDeliveryZone());
                        }
                        if (office.getCity() == null) {
                            office.setCity(mappedNeighborhoods.get(cellValue).getCity());
                        }
                    }
                });
    }

    private void setZone(Customer customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(office -> {
                    if (mappedZones.get(cellValue) != null) {
                        office.setDeliveryZone(mappedZones.get(cellValue));
                    } else {
                        processDefaultZone(office, cellValue);
                    }
                });
    }

    private void processDefaultZone(BranchOffice office, String cellValue) {
        String[] splited = cellValue.split("\\s+");
        String ruta = splited.length > 1 ? splited[1] : splited[0];

        try {
            Integer rutaNumber = Integer.valueOf(ruta);
            String zoneName = rutaNumber >= 1 && rutaNumber <= 4
                    ? "RUTA " + rutaNumber
                    : "EXTERNA";
            office.setDeliveryZone(mappedZones.get(zoneName));
        } catch (Exception e) {
            office.setDeliveryZone(mappedZones.get("EXTERNA"));
        }
    }

    @Override
    protected boolean isValidEntity(Customer entity, Set<String> processedItems, CustomerExcelResponse response) {
        if (entity.getDocumentNumber() == null || entity.getDocumentNumber().isEmpty()) {
            response.addError("Error: El documento es requerido");
            return false;
        }

        if (processedItems.contains(entity.getDocumentNumber())) {
            response.addError("Error: El documento " + entity.getDocumentNumber() + " está duplicado");
            return false;
        }

        return true;
    }

    @Override
    protected void addToProcessedItems(Customer entity, Set<String> processedItems) {
        processedItems.add(entity.getDocumentNumber());
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("documentNumber", "name"),
                List.of("asc", "asc"));
    }

    @Override
    protected Customer createEntity() {
        return Customer.builder()
                .createdAt(Instant.now())
                .createdBy(getCurrentUser())
                .isActive(true)
                .branchOfficeList(new HashSet<>())
                .build();
    }

    @Override
    protected CustomerExcelResponse createResponse() {
        return new CustomerExcelResponse();
    }
}

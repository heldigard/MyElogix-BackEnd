package com.elogix.api.customers.infrastructure.helpers.Customer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.branch_office.BranchOfficeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city_basic.CityBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.contact_person_basic.ContactPersonBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.delivery_zone_basic.DeliveryZoneBasicDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.membership.MembershipDataJpaRepository;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodData;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.neighborhood.NeighborhoodDataJpaRepository;
import com.elogix.api.customers.infrastructure.entry_points.dto.CustomerExcelResponse;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerImportExcelHelper {
    private final ExcelHelper excelHelper;
    private final DocumentTypeDataJpaRepository documentTypeRepository;
    private final NeighborhoodDataJpaRepository neighborhoodRepository;
    private final CityBasicDataJpaRepository cityRepository;
    private final DeliveryZoneBasicDataJpaRepository deliveryZoneRepository;
    private final MembershipDataJpaRepository membershipRepository;
    private final CustomerDataJpaRepository customerRepository;

    public CustomerExcelResponse excelToCustomers(InputStream inputStream) {
        CustomerExcelResponse response = new CustomerExcelResponse();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            HashMap<String, String> customerList = new HashMap<>();

            Map<Integer, String> mapColumns = excelHelper.mapColumns(rows.next());

            List<CustomerData> existingList = customerRepository.findAll();
            Map<String, CustomerData> mappedCustomers = excelHelper.mapEntities(existingList,
                    CustomerData::getDocumentNumber);

            List<DocumentTypeData> existingDocumentTypes = documentTypeRepository.findAll();
            Map<String, DocumentTypeData> mappedDocumentTypeNames = excelHelper.mapEntities(existingDocumentTypes,
                    DocumentTypeData::getName);

            List<NeighborhoodData> existingNeighborhoods = neighborhoodRepository.findAll();
            Map<String, NeighborhoodData> mappedNeighborNames = excelHelper.mapEntities(existingNeighborhoods,
                    NeighborhoodData::getName);

            List<CityBasicData> existingCities = cityRepository.findAll();
            Map<String, CityBasicData> mappedCityNames = excelHelper.mapEntities(existingCities,
                    CityBasicData::getName);

            List<DeliveryZoneBasicData> existingZones = deliveryZoneRepository.findAll();
            Map<String, DeliveryZoneBasicData> mappedZoneNames = excelHelper
                    .mapEntities(existingZones, DeliveryZoneBasicData::getName);

            List<MembershipData> existingMemberships = membershipRepository.findAll();
            Map<String, MembershipData> mappedMembershipNames = excelHelper.mapEntities(existingMemberships,
                    data -> data.getName().toString());

            int numOfNonEmptyCells = excelHelper.getNumberOfNonEmptyCells(sheet, 0);

            for (int rowIndex = 0; rowIndex < numOfNonEmptyCells - 1; rowIndex++) {
                Row currentRow = rows.next();

                Integer documentIndex = excelHelper.getIndexColumn("Documento", mapColumns);
                if (documentIndex == -1) {
                    response.getErrors().add("Could not find 'Documento' column");
                    break;
                }

                Integer nameIndex = excelHelper.getIndexColumn("Nombre", mapColumns);
                if (nameIndex == -1) {
                    response.getErrors().add("Could not find 'Nombre' column");
                    break;
                }

                String cellValue = excelHelper.getCleanCellValue(currentRow.getCell(documentIndex), true);
                if (customerList.containsKey(cellValue)) {
                    String name = excelHelper.getCleanCellValue(currentRow.getCell(nameIndex), true);
                    if (name.equals(customerList.get(cellValue)))
                        response.getErrors().add("Error: Registro duplicado: "
                                + cellValue + " con " + name);
                    continue;
                }

                CustomerData customer;
                if (mappedCustomers.containsKey(cellValue)) {
                    customer = mappedCustomers.get(cellValue);
                    customer.setUpdatedAt(Instant.now());
                } else {
                    customer = new CustomerData();
                    customer.setCreatedAt(Instant.now());
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    int cellIdx = currentCell.getColumnIndex();
                    customer = _setupCustomerFromCell(
                            cellIdx,
                            currentCell,
                            customer,
                            response.getErrors(),
                            mapColumns,
                            mappedDocumentTypeNames,
                            mappedNeighborNames,
                            mappedCityNames,
                            mappedZoneNames,
                            mappedMembershipNames);
                }

                if (!(customerList.containsKey(customer.getDocumentNumber())
                        && customerList.get(customer.getDocumentNumber()).equals(customer.getName()))) {
                    if (customer.getBranchOfficeList().stream().findFirst().get().getAddress() == null) {
                        response.getErrors().add("Error: " + customer.getDocumentNumber() +
                                " El cliente debe tener una dirección");
                        continue;
                    }

                    if (customer.getName() == null) {
                        response.getErrors().add("Error: " + customer.getDocumentNumber() +
                                " El cliente debe tener un nombre");
                        continue;
                    }

                    response.getCustomers().add(customer);
                    customerList.put(customer.getDocumentNumber(), customer.getName());
                } else {
                    response.getErrors().add("Error: Registro duplicado: " + customer.getDocumentNumber());
                }
            }

            workbook.close();

            return response;

        } catch (

        IOException e) {
            response.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private CustomerData _setupCustomerFromCell(
            int cellIdx,
            Cell cell,
            CustomerData customer,
            Set<String> errors,
            Map<Integer, String> mapColumns,
            Map<String, DocumentTypeData> mappedDocumentTypeNames,
            Map<String, NeighborhoodData> mappedNeighborNames,
            Map<String, CityBasicData> mappedCityNames,
            Map<String, DeliveryZoneBasicData> mappedZoneNames,
            Map<String, MembershipData> mappedMembershipNames) {
        final String cellValue = excelHelper.getCleanCellValue(cell, true);

        String columnName = mapColumns.get(cellIdx);

        if ((cellValue.isEmpty() && !columnName.equals("Ciudad"))
                || cellValue.contains("N/A")
                || cellValue.equals("0")) {
            return customer;
        }

        switch (columnName) {
            case "Documento":
                if (customer.getDocumentNumber() == null) {
                    if (cellValue.length() > 6 && cellValue.length() < 15) {
                        customer.setDocumentNumber(cellValue);
                    } else {
                        errors.add("Error: " + cellValue + " El documento debe tener entre 6 y 10 digitos");
                    }
                }

                return customer;

            case "Tipo":
                customer.setDocumentType(mappedDocumentTypeNames.get(cellValue));

                return customer;

            case "Nombre":
                customer.setName(cellValue);

                return customer;

            case "Direccion":
                setBranchOffice(customer, clearAddress(cellValue));

                return customer;

            case "Email":
                String email = excelHelper.getCleanEmailAddress(cellValue);
                if (EmailValidator.getInstance()
                        .isValid(email)) {
                    customer.setEmail(email);
                } else {
                    errors.add("Error: " + email + " El email es invalido," +
                            " customerId: " + customer.getDocumentNumber());
                }

                return customer;

            case "Telefono":
                String phone = clearPhone(cellValue);
                if (phone.length() > 6 && phone.length() < 15) {
                    customer.setPhone(phone);
                } else {
                    errors.add("Error: " + phone + " El telefono debe tener entre 7 y 10 digitos," +
                            " customerId: " + customer.getDocumentNumber());
                }

                return customer;

            case "Contacto":
                setContactPerson(customer, cellValue);

                return customer;

            case "Celular":
                String cellphone = clearPhone(cellValue);
                if (cellphone.length() > 6 && cellphone.length() < 15) {
                    setContactPhone(customer, cellphone);
                } else {
                    errors.add("Error: " + cellphone + " El celular debe tener 10 digitos," +
                            " customerId: " + customer.getDocumentNumber());
                }

                return customer;

            case "Celular2":
                String cellphone2 = clearPhone(cellValue);
                if (cellphone2.length() > 6 && cellphone2.length() < 15) {
                    setContactPhone2(customer, cellphone2);
                } else {
                    errors.add("Error: " + cellphone2 + " El celular debe tener 10 digitos," +
                            " customerId: " + customer.getDocumentNumber());
                }

                return customer;

            case "Ciudad":
                setCity(customer, cellValue, mappedCityNames, mappedNeighborNames);

                return customer;

            case "Sector":
                setNeighborhood(customer, cellValue, mappedNeighborNames);

                return customer;

            case "Ruta":
                setDeliveryZone(customer, cellValue, mappedZoneNames);

                return customer;

            case "Membresia":
                String membresia = cellValue
                        .replace("(", "")
                        .replace(")", "")
                        .trim();
                customer.setMembership(mappedMembershipNames.get(membresia));

                return customer;

            default:
                return customer;
        }
    }

    private void setBranchOffice(CustomerData customer, String cellValue) {
        if (customer.getBranchOfficeList() == null) {
            customer.setBranchOfficeList(new HashSet<>());
        }

        if (customer.getBranchOfficeList().isEmpty()) {
            customer.getBranchOfficeList().add(new BranchOfficeData());
        }

        BranchOfficeData branchOfficeData = customer.getBranchOfficeList().iterator().next();
        branchOfficeData.setCustomerId(customer.getId());
        branchOfficeData.setAddress(cellValue);
    }

    private String clearAddress(String address) {
        return address
                .replace(" NO ", "")
                .replace("#", "");
    }

    private void setContactPerson(CustomerData customer, String cellValue) {
        if (customer.getBranchOfficeList().isEmpty()) {
            customer.getBranchOfficeList().add(new BranchOfficeData());
        }

        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(branchOfficeData -> branchOfficeData
                        .setContactPerson(ContactPersonBasicData.builder().name(cellValue).build()));
    }

    private void setContactPhone(CustomerData customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(branchOfficeData -> {
                    if (branchOfficeData.getContactPerson() != null) {
                        branchOfficeData.getContactPerson().setMobileNumberPrimary(cellValue);
                    }
                });
    }

    private void setContactPhone2(CustomerData customer, String cellValue) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(branchOfficeData -> {
                    if (branchOfficeData.getContactPerson() != null) {
                        branchOfficeData.getContactPerson().setMobileNumberSecondary(cellValue);
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

    private void setCity(
            CustomerData customer,
            String cellValue,
            Map<String, CityBasicData> mappedCityNames,
            Map<String, NeighborhoodData> mappedNeighborNames) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(branchOfficeData -> {
                    if (mappedCityNames.get(cellValue) != null) {
                        branchOfficeData.setCity(mappedCityNames.get(cellValue));
                    } else if (mappedNeighborNames.get(cellValue) != null) {
                        branchOfficeData.setCity(mappedNeighborNames.get(cellValue).getCity());
                    }
                });
    }

    private void setNeighborhood(
            CustomerData customer,
            String cellValue,
            Map<String, NeighborhoodData> mappedNeighborNames) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(branchOfficeData -> {
                    if (mappedNeighborNames.get(cellValue) != null) {
                        branchOfficeData.setNeighborhood(mappedNeighborNames.get(cellValue));
                        if (branchOfficeData.getDeliveryZone() == null) {
                            branchOfficeData.setDeliveryZone(mappedNeighborNames.get(cellValue).getDeliveryZone());
                        }
                        if (branchOfficeData.getCity() == null) {
                            branchOfficeData.setCity(mappedNeighborNames.get(cellValue).getCity());
                        }
                    } else if (branchOfficeData.getAddress() != null) {
                        String[] splited = cellValue.split("\\s+");
                        String barrio = splited[splited.length - 1];

                        if (mappedNeighborNames.get(barrio) != null) {
                            if (branchOfficeData.getDeliveryZone() == null) {
                                branchOfficeData.setDeliveryZone(mappedNeighborNames.get(cellValue).getDeliveryZone());
                            }
                            if (branchOfficeData.getCity() == null) {
                                branchOfficeData.setCity(mappedNeighborNames.get(barrio).getCity());
                            }
                        }
                    }
                });
    }

    private void setDeliveryZone(
            CustomerData customer,
            String cellValue,
            Map<String, DeliveryZoneBasicData> mappedZoneNames) {
        customer.getBranchOfficeList().stream()
                .findFirst()
                .ifPresent(branchOfficeData -> {
                    if (mappedZoneNames.get(cellValue) != null) {
                        branchOfficeData.setDeliveryZone(mappedZoneNames.get(cellValue));
                    } else {
                        String[] splited = cellValue.split("\\s+");
                        String ruta = splited.length > 1 ? splited[1] : splited[0];

                        try {
                            Integer rutaNumber = Integer.valueOf(ruta);

                            switch (rutaNumber) {
                                case 1:
                                    branchOfficeData.setDeliveryZone(mappedZoneNames.get("RUTA 1"));
                                    break;
                                case 2:
                                    branchOfficeData.setDeliveryZone(mappedZoneNames.get("RUTA 2"));
                                    break;
                                case 3:
                                    branchOfficeData.setDeliveryZone(mappedZoneNames.get("RUTA 3"));
                                    break;
                                case 4:
                                    branchOfficeData.setDeliveryZone(mappedZoneNames.get("RUTA 4"));
                                    break;
                                default:
                                    branchOfficeData.setDeliveryZone(mappedZoneNames.get("EXTERNA"));
                                    break;
                            }
                        } catch (Exception e) {
                            branchOfficeData.setDeliveryZone(mappedZoneNames.get("EXTERNA"));
                        }
                    }
                });
    }
}

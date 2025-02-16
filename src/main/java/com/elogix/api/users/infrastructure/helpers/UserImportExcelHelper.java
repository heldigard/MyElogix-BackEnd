package com.elogix.api.users.infrastructure.helpers;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericImportExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;
import com.elogix.api.users.domain.model.Office;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.usecase.OfficeUseCase;
import com.elogix.api.users.domain.usecase.RoleUseCase;
import com.elogix.api.users.domain.usecase.UserUseCase;
import com.elogix.api.users.infrastructure.entry_points.dto.UserExcelResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserImportExcelHelper extends GenericImportExcelHelper<UserModel, UserExcelResponse> {
    private final UserUseCase useCase;
    private final PasswordEncoder passwordEncoder;
    private final RoleUseCase roleUseCase;
    private final OfficeUseCase officeUseCase;
    private Map<String, RoleModel> mappedRoles;
    private Map<String, Office> mappedOffices;

    public UserImportExcelHelper(
            ExcelHelper excelHelper,
            UpdateUtils updateUtils,
            UserUseCase useCase,
            PasswordEncoder passwordEncoder,
            RoleUseCase roleUseCase,
            OfficeUseCase officeUseCase) {
        super(excelHelper, updateUtils);
        this.useCase = useCase;
        this.passwordEncoder = passwordEncoder;
        this.roleUseCase = roleUseCase;
        this.officeUseCase = officeUseCase;
    }

    protected void initializeMaps() {
        List<Sort.Order> sortOrders = SortOrderMapper.createSortOrders(
                List.of("name"),
                List.of("asc"));

        List<RoleModel> roles = roleUseCase.findAll(sortOrders, false);
        this.mappedRoles = excelHelper.mapEntities(roles, role -> role.getName().toString());

        List<Office> offices = officeUseCase.findAll(sortOrders, false);
        this.mappedOffices = excelHelper.mapEntities(offices, Office::getName);
    }

    @Override
    protected Set<String> getRequiredColumns() {
        return Set.of("Username", "Password", "Nombre", "Apellido", "Email", "Roles", "Oficina");
    }

    @Override
    protected String getEntityIdentifier(UserModel user) {
        return user.getUsername();
    }

    @Override
    protected boolean isIdentifierColumn(String columnName) {
        return "Username".equals(columnName);
    }

    @Override
    protected UserModel processCell(
            int cellIdx,
            Cell cell,
            UserModel user,
            UserExcelResponse response,
            Map<Integer, String> mapColumns) {

        String cellValue = excelHelper.getCleanCellValue(cell, false);
        String columnName = mapColumns.get(cellIdx);

        if (cellValue.isEmpty() || cellValue.contains("N/A")) {
            return user;
        }

        switch (columnName) {
            case "Username":
                user.setUsername(cellValue.toLowerCase());
                break;

            case "Password":
                if (cellValue.length() >= 6) {
                    user.setPassword(passwordEncoder.encode(cellValue));
                } else {
                    response.addError("Error: Password debe tener al menos 6 caracteres: %s", cellValue);
                }
                break;

            case "Nombre":
                user.setFirstName(cellValue.toUpperCase());
                break;

            case "Apellido":
                user.setLastName(cellValue.toUpperCase());
                break;

            case "Email":
                String email = excelHelper.getCleanEmailAddress(cellValue);
                if (EmailValidator.isValidEmail(email)) {
                    user.setEmail(email);
                } else {
                    response.addError("Error: Email inválido: %s", cellValue);
                }
                break;

            case "Roles":
                setRoles(user, cellValue);
                break;

            case "Oficina":
                setOffice(user, cellValue.toUpperCase());
                break;

            case "Telefono":
                user.setPhone(cellValue);
                break;
        }

        return user;
    }

    private void setRoles(UserModel user, String rolesStr) {
        String[] roles = rolesStr.split(",");
        for (String role : roles) {
            String roleName = role.trim().toUpperCase();
            if (mappedRoles.containsKey(roleName)) {
                user.getRoles().add(mappedRoles.get(roleName));
            }
        }
    }

    private void setOffice(UserModel user, String officeName) {
        if (mappedOffices.containsKey(officeName)) {
            user.setOffice(mappedOffices.get(officeName));
        } else {
            user.setOffice(Office.builder().name(officeName).build());
            mappedOffices.put(officeName, user.getOffice());
        }
    }

    @Override
    protected boolean isValidEntity(UserModel user, Set<String> processedItems, UserExcelResponse response) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            response.addError("Error: Username es requerido");
            return false;
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            response.addError("Error: Password es requerido para el usuario %s", user.getUsername());
            return false;
        }

        if (processedItems.contains(user.getUsername())) {
            response.addError("Error: Usuario %s está duplicado", user.getUsername());
            return false;
        }

        return true;
    }

    @Override
    protected List<UserModel> findAllEntities() {
        initializeMaps();
        return useCase.findAll(getSortOrders(), false);
    }

    @Override
    protected void addToProcessedItems(UserModel entity, Set<String> processedItems) {
        if (entity != null && entity.getUsername() != null) {
            processedItems.add(entity.getUsername());
        }
    }

    @Override
    protected UserModel createEntity() {
        Set<RoleModel> defaultRoles = new HashSet<>();
        RoleModel userRole = mappedRoles.get("USER");
        if (userRole != null) {
            defaultRoles.add(userRole);
        }

        return UserModel.builder()
                .createdAt(Instant.now())
                .createdBy(getCurrentUser())
                .roles(defaultRoles)
                .build();
    }

    @Override
    protected UserExcelResponse createResponse() {
        return new UserExcelResponse();
    }

    @Override
    protected List<Sort.Order> getSortOrders() {
        return SortOrderMapper.createSortOrders(
                List.of("username"),
                List.of("asc"));
    }
}

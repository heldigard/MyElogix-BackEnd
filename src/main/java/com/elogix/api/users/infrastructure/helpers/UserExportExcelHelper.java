package com.elogix.api.users.infrastructure.helpers;

import java.util.Set;
import java.util.StringJoiner;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericExportExcelHelper;
import com.elogix.api.users.domain.model.RoleModel;
import com.elogix.api.users.domain.model.UserModel;

@Component
public class UserExportExcelHelper extends GenericExportExcelHelper<UserModel> {

    @Override
    protected String getSheetName() {
        return "Users";
    }

    @Override
    protected String[] getHeaders() {
        return new String[] {
                "Username",
                "Nombre",
                "Apellido",
                "Email",
                "Oficina",
                "Telefono",
                "Roles",
                "Password"
        };
    }

    @Override
    protected void writeEntityToRow(UserModel user, Row row) {
        row.createCell(0).setCellValue(user.getUsername());
        row.createCell(1).setCellValue(user.getFirstName());
        row.createCell(2).setCellValue(user.getLastName());
        row.createCell(3).setCellValue(user.getEmail());
        row.createCell(4).setCellValue(user.getOffice() != null ? user.getOffice().getName() : "");
        row.createCell(5).setCellValue(user.getPhone());

        // Handle roles
        Set<RoleModel> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            StringJoiner rolesJoiner = new StringJoiner(", ");
            for (RoleModel role : roles) {
                rolesJoiner.add(role.getName().name());
            }
            row.createCell(6).setCellValue(rolesJoiner.toString());
        } else {
            row.createCell(6).setCellValue("");
        }

        row.createCell(7).setCellValue("");
    }
}

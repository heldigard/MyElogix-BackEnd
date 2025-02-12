package com.elogix.api.users.infrastructure.helpers;

import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserData;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Component
@AllArgsConstructor
public class UserExportExcelHelper {
    public ByteArrayInputStream usersToExcel(List<UserData> users) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String[] HEADERs = {
                "Username",
                "Nombre",
                "Apellido",
                "Email",
                "Oficina",
                "Telefono",
                "Roles",
                "Password",
        };

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (UserData user : users) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(user.getUsername());
                row.createCell(1).setCellValue(user.getFirstName());
                row.createCell(2).setCellValue(user.getLastName());
                row.createCell(3).setCellValue(user.getEmail());
                row.createCell(4).setCellValue(user.getOffice() == null ? "" : user.getOffice().getName());
                row.createCell(5).setCellValue(user.getPhone());

                Set<RoleData> roles = user.getRoles();
                if (roles != null && !roles.isEmpty()) {
                    StringJoiner rolesJoiner = new StringJoiner(", ");
                    for (RoleData role : roles) {
                        rolesJoiner.add(role.getName().name());
                    }
                    row.createCell(6).setCellValue(rolesJoiner.toString());
                } else {
                    row.createCell(6).setCellValue("");
                }

                row.createCell(7).setCellValue("");
            }

            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}

package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user;

import com.elogix.api.users.infrastructure.entry_points.dto.UserExcelResponse;
import com.elogix.api.users.infrastructure.helpers.UserExportExcelHelper;
import com.elogix.api.users.infrastructure.helpers.UserImportExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class UserExcelService {
    private final UserDataJpaRepository repository;
    private final UserImportExcelHelper importExcelHelper;
    private final UserExportExcelHelper exportExcelHelper;

    public UserExcelResponse importExcelFile(MultipartFile file) {
        UserExcelResponse usersResponse = new UserExcelResponse();
        try {
            usersResponse = importExcelHelper.excelToUsers(file.getInputStream());
            repository.saveAll(usersResponse.getUsers());
        } catch (IOException e) {
            usersResponse.getErrors().add(e.getMessage());
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }

        return usersResponse;
    }

    public ByteArrayInputStream exportExcelFile() {
        List<UserData> users = repository.findAll();

        ByteArrayInputStream in = exportExcelHelper.usersToExcel(users);
        return in;
    }
}

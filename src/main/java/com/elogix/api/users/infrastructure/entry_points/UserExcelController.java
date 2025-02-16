package com.elogix.api.users.infrastructure.entry_points;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.generics.infrastructure.entry_points.GenericExcelController;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserExcelService;
import com.elogix.api.users.infrastructure.entry_points.dto.UserExcelResponse;

@RestController
@RequestMapping("/api/v1/user/excel")
public class UserExcelController extends GenericExcelController<UserModel, UserExcelResponse> {

    public UserExcelController(
            ExcelHelper excelHelper,
            UserExcelService excelService) {
        super(excelHelper, excelService);
    }

    @Override
    protected String getDownloadFilename() {
        return "users.xlsx";
    }
}

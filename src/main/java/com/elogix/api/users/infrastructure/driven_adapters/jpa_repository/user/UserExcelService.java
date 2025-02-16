package com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.elogix.api.generics.infrastructure.helpers.excel.GenericExcelService;
import com.elogix.api.shared.infraestructure.helpers.mappers.SortOrderMapper;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.model.gateways.UserGateway;
import com.elogix.api.users.domain.usecase.UserUseCase;
import com.elogix.api.users.infrastructure.entry_points.dto.UserExcelResponse;
import com.elogix.api.users.infrastructure.helpers.UserExportExcelHelper;
import com.elogix.api.users.infrastructure.helpers.UserImportExcelHelper;

@Component
public class UserExcelService extends GenericExcelService<UserModel, UserExcelResponse, UserGateway, UserUseCase> {

    public UserExcelService(
            UserUseCase useCase,
            UserImportExcelHelper importExcelHelper,
            UserExportExcelHelper exportExcelHelper) {
        super(useCase, importExcelHelper, exportExcelHelper);
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

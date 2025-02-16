package com.elogix.api.generics.infrastructure.helpers.excel;

import java.io.ByteArrayInputStream;

import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.generics.infrastructure.dto.ExcelResponse;

public interface ExcelService<T, R extends ExcelResponse<T>> {
    R importExcelFile(MultipartFile file);

    ByteArrayInputStream exportExcelFile();
}

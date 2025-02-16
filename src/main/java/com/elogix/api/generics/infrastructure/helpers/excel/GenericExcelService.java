package com.elogix.api.generics.infrastructure.helpers.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.generics.domain.gateway.GenericGateway;
import com.elogix.api.generics.domain.model.GenericEntity;
import com.elogix.api.generics.domain.usecase.GenericUseCase;
import com.elogix.api.generics.infrastructure.dto.ExcelResponse;
import com.elogix.api.generics.infrastructure.exception.ExcelProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public abstract class GenericExcelService<T extends GenericEntity, R extends ExcelResponse<T>, G extends GenericGateway<T>, U extends GenericUseCase<T, G>>
    implements ExcelService<T, R> {

    protected final U useCase;
    protected final GenericImportExcelHelper<T, R> importHelper;
    protected final GenericExportExcelHelper<T> exportHelper;

    protected GenericExcelService(
            U useCase,
            GenericImportExcelHelper<T, R> importHelper,
            GenericExportExcelHelper<T> exportHelper) {
        this.useCase = useCase;
        this.importHelper = importHelper;
        this.exportHelper = exportHelper;
    }

    @Override
    public R importExcelFile(MultipartFile file) {
        validateFile(file);
        R response = createResponse();

        try {
            processAndSaveFile(file, response);
            return response; // Retornamos la respuesta después del procesamiento
        } catch (IOException e) {
            handleImportError(e, response);
            return response; // Retornamos la respuesta incluso en caso de error
        }
    }

    @Override
    public ByteArrayInputStream exportExcelFile() {
        try {
            List<Sort.Order> sortOrders = getSortOrders();
            if (sortOrders == null || sortOrders.isEmpty()) {
                throw new ExcelProcessingException("Sort orders cannot be null or empty");
            }

            List<T> entities = useCase.findAll(sortOrders, false);
            if (entities == null) {
                throw new ExcelProcessingException("No data found to export");
            }

            return exportHelper.entitiesToExcel(entities);
        } catch (Exception e) {
            log.error("Error exporting excel file", e);
            throw new ExcelProcessingException("Failed to export excel data", e);
        }
    }

    protected void validateFile(MultipartFile file) {
        if (file == null) {
            throw new ExcelProcessingException("File cannot be null");
        }
        if (file.isEmpty()) {
            throw new ExcelProcessingException("File cannot be empty");
        }
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            throw new ExcelProcessingException("Only .xlsx files are supported");
        }
    }

    protected void processAndSaveFile(MultipartFile file, R response) throws IOException {
        R processedResponse = importHelper.processExcel(file.getInputStream());

        // Copiar los resultados al response original
        response.getErrors().addAll(processedResponse.getErrors());

        if (processedResponse.getErrors().isEmpty() && !processedResponse.getEntities().isEmpty()) {
            try {
                useCase.saveAll(processedResponse.getEntities());
                response.getEntities().addAll(processedResponse.getEntities());
            } catch (Exception e) {
                log.error("Error saving entities", e);
                response.getErrors().add("Error saving data: " + e.getMessage());
            }
        }
    }

    protected void handleImportError(Exception e, R response) {
        String errorMessage = "Failed to process excel data: " + e.getMessage();
        log.error(errorMessage, e);
        response.getErrors().add(errorMessage);
        // No lanzamos la excepción aquí para permitir que el cliente reciba la respuesta con los errores
    }

    protected abstract R createResponse();

    protected abstract List<Sort.Order> getSortOrders();
}

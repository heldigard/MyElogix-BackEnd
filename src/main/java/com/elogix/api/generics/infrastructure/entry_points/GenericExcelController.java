package com.elogix.api.generics.infrastructure.entry_points;

import static org.springframework.http.MediaType.*;

import java.util.stream.Collectors;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.generics.infrastructure.dto.ExcelResponse;
import com.elogix.api.generics.infrastructure.helpers.excel.ExcelService;
import com.elogix.api.shared.infraestructure.entry_points.dto.ResponseMessage;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public abstract class GenericExcelController<T, R extends ExcelResponse<T>> {

    protected final ExcelHelper excelHelper;
    protected final ExcelService<T, R> excelService;

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> upload(@RequestParam("file") MultipartFile file) {
        ResponseMessage.ResponseMessageBuilder rb = ResponseMessage.builder();

        if (!excelHelper.hasExcelFormat(file)) {
            return getBadRequestResponse(rb);
        }

        try {
            R response = excelService.importExcelFile(file);
            return getSuccessResponse(file, response, rb);
        } catch (Exception e) {
            return getErrorResponse(file, e, rb);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() {
        String filename = getDownloadFilename();
        InputStreamResource file = new InputStreamResource(excelService.exportExcelFile());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    protected ResponseEntity<ResponseMessage> getBadRequestResponse(ResponseMessage.ResponseMessageBuilder rb) {
        rb.message("Please upload an excel file!");
        rb.success(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rb.build());
    }

    protected ResponseEntity<ResponseMessage> getSuccessResponse(
            MultipartFile file,
            R response,
            ResponseMessage.ResponseMessageBuilder rb) {

        String message = String.format("Uploaded the file successfully: %s\n", file.getOriginalFilename());
        String result = response.getErrors().stream()
                .map(text -> text + "\n")
                .collect(Collectors.joining("-", "{", "}"));

        message += String.format(" Total Created/Updated: %d\n", response.getEntities().size());
        message += String.format(" Errors: %s\n", result);

        rb.message(message);
        rb.success(true);

        return ResponseEntity.status(HttpStatus.OK).body(rb.build());
    }

    protected ResponseEntity<ResponseMessage> getErrorResponse(
            MultipartFile file,
            Exception e,
            ResponseMessage.ResponseMessageBuilder rb) {

        String message = String.format("Could not upload the file: %s!\n", file.getOriginalFilename());
        message += String.format(" Errors: %s\n", e.getMessage());

        rb.message(message);
        rb.success(false);

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(rb.build());
    }

    protected abstract String getDownloadFilename();
}

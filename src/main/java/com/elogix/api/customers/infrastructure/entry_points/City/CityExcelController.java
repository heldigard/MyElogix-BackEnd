package com.elogix.api.customers.infrastructure.entry_points.City;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.city.CityExcelService;
import com.elogix.api.customers.infrastructure.entry_points.dto.CityExcelResponse;
import com.elogix.api.shared.infraestructure.entry_points.dto.ResponseMessage;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/city/excel")
@RequiredArgsConstructor
public class CityExcelController {
    private final ExcelHelper excelHelper;
    private final CityExcelService excelService;

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> add(@RequestParam("file") MultipartFile file) {
        String message = "";
        ResponseMessage.ResponseMessageBuilder rb = ResponseMessage.builder();

        if (excelHelper.hasExcelFormat(file)) {
            try {
                CityExcelResponse response = excelService.importExcelFile(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + "\n";
                String result = response.getErrors().stream()
                        .map(text -> text + "\n")
                        .collect(Collectors.joining("-", "{", "}"));
                message = message + " Total Creados: " + response.getCities().size() + "\n";
                message = message + " Errors: " + result + "\n";

                rb.message(message);
                rb.success(true);

                return ResponseEntity.status(HttpStatus.OK).body(rb.build());
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!\n";
                message = message + " Errors: " + e.getMessage() + "\n";

                rb.message(message);
                rb.success(false);

                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(rb.build());
            }
        }

        message = "Please upload an excel file!";
        rb.message(message);
        rb.success(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rb.build());
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "cities.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.exportExcelFile());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}

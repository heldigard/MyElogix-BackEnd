package com.tarapaca.api.product.infrastructure.entry_point.product;

import com.tarapaca.api.product.dto.ProductExcelResponse;
import com.tarapaca.api.product.infrastructure.repository.product.ProductExcelService;
import com.tarapaca.api.shared.infraestructure.entry_points.dto.ResponseMessage;
import com.tarapaca.api.users.infrastructure.helpers.ExcelHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/product/excel")
@RequiredArgsConstructor
public class ProductExcelController {
    private final ExcelHelper excelHelper;
    private final ProductExcelService excelService;

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> add(@RequestParam("file") MultipartFile file) {
        String message = "";
        ResponseMessage.ResponseMessageBuilder rb = ResponseMessage.builder();

        if (excelHelper.hasExcelFormat(file)) {
            try {
                ProductExcelResponse response = excelService.importExcelFile(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + "\n";
                String result = response.getErrors().stream()
                        .map(text -> text + "\n")
                        .collect(Collectors.joining("-", "{", "}"));
                message = message + " Total Creados: " + response.getProducts().size() + "\n";
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
        String filename = "products.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.exportExcelFile());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}

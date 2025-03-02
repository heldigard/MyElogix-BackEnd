package com.elogix.api.product.infrastructure.entry_point.product_category;

import com.elogix.api.product.dto.ProductCategoryExcelResponse;
import com.elogix.api.product.infrastructure.repository.product_category.ProductCategoryExcelService;
import com.elogix.api.shared.infraestructure.entry_points.dto.ResponseMessage;
import com.elogix.api.shared.infraestructure.helpers.ExcelHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/productCategory/excel")
@RequiredArgsConstructor
public class ProductCategoryExcelController {
    private final ExcelHelper excelHelper;
    private final ProductCategoryExcelService excelService;

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> add(@RequestParam("file") MultipartFile file) {
        String message = "";
        ResponseMessage.ResponseMessageBuilder rb = ResponseMessage.builder();

        if (excelHelper.hasExcelFormat(file)) {
            try {
                ProductCategoryExcelResponse response = excelService.importExcelFile(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + "\n";
                String result = response.getErrors().stream()
                        .map(text -> text + "\n")
                        .collect(Collectors.joining("-", "{", "}"));
                message = message + " Total Creados: " + response.getProductCategories().size() + "\n";
                message = message + " Errors: " + result + "\n";

                rb.message(message);
                rb.success(true);

                return ResponseEntity.status(HttpStatus.OK).body(rb.build());
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
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

//    @GetMapping("/download")
//    public ResponseEntity<Resource> getFile() {
//        String filename = "products.xlsx";
//        InputStreamResource file = new InputStreamResource(excelService.exportExcelFile());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(file);
//    }
}

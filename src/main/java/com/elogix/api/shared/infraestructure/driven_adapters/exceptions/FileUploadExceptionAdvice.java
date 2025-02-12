package com.elogix.api.shared.infraestructure.driven_adapters.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.elogix.api.shared.infraestructure.entry_points.dto.ResponseMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice {
    ResponseMessage.ResponseMessageBuilder rb = ResponseMessage.builder();

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        rb.message("File too large!");
        rb.success(false);

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(rb.build());
    }
}

package com.elogix.api.shared.infraestructure.entry_points;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/live")
@RequiredArgsConstructor
public class Live {
    @GetMapping(value = "/ready")
    public ResponseEntity<Boolean> isReady() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

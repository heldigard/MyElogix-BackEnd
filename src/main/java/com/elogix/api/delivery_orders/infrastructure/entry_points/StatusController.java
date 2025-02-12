package com.elogix.api.delivery_orders.infrastructure.entry_points;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.delivery_orders.domain.model.EStatus;
import com.elogix.api.delivery_orders.domain.model.Status;
import com.elogix.api.delivery_orders.domain.model.gateways.StatusGateway;
import com.elogix.api.delivery_orders.domain.usecase.StatusUseCase;
import com.elogix.api.generics.infrastructure.dto.ApiResponse;
import com.elogix.api.generics.infrastructure.entry_points.GenericBasicController;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/status")
public class StatusController extends GenericBasicController<Status, StatusGateway, StatusUseCase> {
    public StatusController(StatusUseCase useCase) {
        super(useCase);
    }

    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<Status>> findByName(
            @NotBlank @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            log.info("Searching entity by name: {} (includeDeleted: {})", name, includeDeleted);
            Status status = useCase.findByName(EStatus.valueOf(name.toUpperCase()), includeDeleted);

            if (status == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Entity not found with name: " + name, null));
            }

            return ResponseEntity.ok(new ApiResponse<>("Entity found successfully", status));
        } catch (Exception e) {
            log.error("Error searching entity by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error searching entity: " + e.getMessage(), null));
        }
    }

    @GetMapping("/search/name/batch")
    public ResponseEntity<ApiResponse<Status>> findByNameIn(
            @NotBlank @RequestParam List<String> names,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            log.info("Searching entity by name: {} (includeDeleted: {})", names, includeDeleted);
            List<EStatus> statusNames = names.stream()
                    .map(name -> EStatus.valueOf(name.toUpperCase()))
                    .toList();
            List<Status> statuses = useCase.findByNameIn(statusNames, includeDeleted);

            if (statuses == null || statuses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Entities not found with names: " + names, null));
            }

            return ResponseEntity.ok(new ApiResponse<>("Entities found successfully", statuses));
        } catch (Exception e) {
            log.error("Error searching entities by names: {}", names, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error searching entities: " + e.getMessage(), null));
        }
    }
}

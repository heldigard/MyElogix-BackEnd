package com.tarapaca.api.customers.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.customers.domain.model.EMembership;
import com.tarapaca.api.customers.domain.model.Membership;
import com.tarapaca.api.customers.domain.model.gateways.MembershipGateway;
import com.tarapaca.api.customers.domain.usecase.MembershipUseCase;
import com.tarapaca.api.generics.infrastructure.dto.ApiResponse;
import com.tarapaca.api.generics.infrastructure.entry_points.GenericController;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/membership")
public class MembershipController
        extends GenericController<Membership, MembershipGateway, MembershipUseCase> {

    public MembershipController(MembershipUseCase useCase) {
        super(useCase);
    }

    @DeleteMapping("/name/{name}")
    @PreAuthorize("hasAnyAuthority('manager:*', 'admin:*')")
    public ResponseEntity<ApiResponse<Membership>> deleteByName(@PathVariable String name, boolean includeDeleted) {
        try {
            log.info("Deleting entity with name: {}", name);
            Membership membership = useCase.deleteByName(EMembership.valueOf(name));
            return ResponseEntity.ok(new ApiResponse<Membership>("Entity deleted successfully", membership));
        } catch (Exception e) {
            log.error("Error deleting entity: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<Membership>("Error deleting entity: " + e.getMessage()));
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<Membership>> findByName(
            @NotBlank @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        try {
            log.info("Searching entity by name: {} (includeDeleted: {})", name, includeDeleted);
            Membership membership = useCase.findByName(EMembership.valueOf(name), includeDeleted);

            if (membership == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Entity not found with name: " + name));
            }

            return ResponseEntity.ok(new ApiResponse<>("Entity found successfully", membership));
        } catch (Exception e) {
            log.error("Error searching entity by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error searching entity: " + e.getMessage(), null));
        }
    }
}

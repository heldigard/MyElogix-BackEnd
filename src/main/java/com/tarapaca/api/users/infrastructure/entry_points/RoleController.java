package com.tarapaca.api.users.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.generics.infrastructure.entry_points.GenericController;
import com.tarapaca.api.users.domain.model.ERole;
import com.tarapaca.api.users.domain.model.RoleModel;
import com.tarapaca.api.users.domain.model.gateways.RoleGateway;
import com.tarapaca.api.users.domain.usecase.RoleUseCase;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController extends GenericController<RoleModel, RoleGateway, RoleUseCase> {
    public RoleController(RoleUseCase useCase) {
        super(useCase);
    }

    @DeleteMapping("/name")
    public ResponseEntity<?> deleteByName(
            @RequestParam ERole name,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        useCase.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/username")
    public ResponseEntity<RoleModel> findByName(
            @RequestParam ERole name,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        RoleModel user = useCase.findByName(name, isDeleted);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

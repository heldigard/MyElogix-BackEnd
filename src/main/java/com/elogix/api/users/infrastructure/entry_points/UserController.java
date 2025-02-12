package com.elogix.api.users.infrastructure.entry_points;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.authentication.dto.AuthenticationResponse;
import com.elogix.api.authentication.dto.RegisterRequest;
import com.elogix.api.authentication.infrastructure.repository.AuthenticationService;
import com.elogix.api.generics.infrastructure.entry_points.GenericController;
import com.elogix.api.users.domain.model.ERole;
import com.elogix.api.users.domain.model.UserModel;
import com.elogix.api.users.domain.model.gateways.UserGateway;
import com.elogix.api.users.domain.usecase.UserUseCase;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.role.RoleData;
import com.elogix.api.users.infrastructure.helpers.mappers.RoleMapper;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends GenericController<UserModel, UserGateway, UserUseCase> {
    private final RoleMapper roleMapper;
    private final AuthenticationService service;

    public UserController(UserUseCase useCase, RoleMapper roleMapper, AuthenticationService service) {
        super(useCase);
        this.roleMapper = roleMapper;
        this.service = service;
    }

    @PutMapping("/update/roles")
    public ResponseEntity<Integer> updateRole(@RequestBody Long id, @RequestBody Set<String> rolesList) {
        Set<RoleData> roles = rolesList.stream()
                .map(role -> RoleData.builder()
                        .name(ERole.valueOf(role.toUpperCase()))
                        .build())
                .collect(Collectors.toSet());
        int response = useCase.updateRole(id, roleMapper.toDomain(roles).stream().collect(Collectors.toSet()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/email")
    public ResponseEntity<?> deleteByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        useCase.deleteByEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/username")
    public ResponseEntity<?> deleteByUsername(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        useCase.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/find/email")
    public ResponseEntity<UserModel> findByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        UserModel user = useCase.findByEmail(email, isDeleted);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/find/username")
    public ResponseEntity<UserModel> findByUsername(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        UserModel user = useCase.findByUsername(username, isDeleted);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
}

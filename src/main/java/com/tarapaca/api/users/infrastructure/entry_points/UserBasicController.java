package com.tarapaca.api.users.infrastructure.entry_points;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarapaca.api.generics.infrastructure.entry_points.GenericBasicController;
import com.tarapaca.api.users.domain.model.UserBasic;
import com.tarapaca.api.users.domain.model.gateways.UserBasicGateway;
import com.tarapaca.api.users.domain.usecase.UserBasicUseCase;

@RestController
@RequestMapping("/api/v1/userBasic")
public class UserBasicController extends GenericBasicController<UserBasic, UserBasicGateway, UserBasicUseCase> {

    public UserBasicController(UserBasicUseCase useCase) {
        super(useCase);
    }

    @GetMapping(value = "/username")
    public ResponseEntity<UserBasic> findByUsername(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        UserBasic user = useCase.findByUsername(username, isDeleted);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

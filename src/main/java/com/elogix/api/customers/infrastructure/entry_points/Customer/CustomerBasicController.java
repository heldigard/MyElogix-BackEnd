package com.elogix.api.customers.infrastructure.entry_points.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.CustomerBasic;
import com.elogix.api.customers.domain.model.gateways.CustomerBasicGateway;
import com.elogix.api.customers.domain.usecase.CustomerBasicUseCase;
import com.elogix.api.generics.infrastructure.entry_points.GenericNamedBasicController;

@RestController
@RequestMapping("/api/v1/customer-basic")
public class CustomerBasicController
        extends GenericNamedBasicController<CustomerBasic, CustomerBasicGateway, CustomerBasicUseCase> {

    public CustomerBasicController(CustomerBasicUseCase useCase) {
        super(useCase);

    }

    @GetMapping(value = "/find/email")
    public ResponseEntity<CustomerBasic> findByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        var customerBasic = useCase.findByEmail(email, isDeleted);
        if (customerBasic.getId() != null) {
            return new ResponseEntity<>(customerBasic, HttpStatus.OK);
        }
        return new ResponseEntity<>(customerBasic, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/documentNumber")
    public ResponseEntity<CustomerBasic> findByDocumentNumber(
            @RequestParam String documentNumber,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        var customerBasic = useCase.findByDocumentNumber(documentNumber, isDeleted);
        if (customerBasic.getId() != null) {
            return new ResponseEntity<>(customerBasic, HttpStatus.OK);
        }
        return new ResponseEntity<>(customerBasic, HttpStatus.NOT_FOUND);
    }
}

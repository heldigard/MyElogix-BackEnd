package com.elogix.api.customers.infrastructure.entry_points.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.gateways.CustomerGateway;
import com.elogix.api.customers.domain.usecase.CustomerUseCase;
import com.elogix.api.generics.infrastructure.entry_points.GenericNamedController;
import com.elogix.api.shared.domain.model.Hits;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController
        extends GenericNamedController<Customer, CustomerGateway, CustomerUseCase> {

    public CustomerController(CustomerUseCase useCase) {
        super(useCase);
    }

    @GetMapping(value = "/find/email")
    public ResponseEntity<Customer> findByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        Customer customer = useCase.findByEmail(email, isDeleted);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping(value = "/find/documentNumber")
    public ResponseEntity<Customer> findByDocumentNumber(
            @RequestParam String documentNumber,
            @RequestParam(defaultValue = "0") boolean isDeleted) {
        Customer customer = useCase.findByDocumentNumber(documentNumber, isDeleted);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/update/hits")
    public ResponseEntity<Integer> updateHits(@RequestBody Hits hits) {
        int response = useCase.updateHits(hits);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/increment/hits")
    public ResponseEntity<Integer> incrementHits(@RequestBody Hits hits) {
        int response = useCase.incrementHits(hits);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

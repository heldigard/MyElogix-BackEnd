package com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.customer;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.data.repository.CrudRepository;

import com.elogix.api.customers.domain.model.BranchOffice;
import com.elogix.api.customers.domain.model.Customer;
import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.domain.model.Membership;
import com.elogix.api.customers.domain.model.gateways.CustomerGateway;
import com.elogix.api.customers.domain.usecase.BranchOfficeUseCase;
import com.elogix.api.customers.domain.usecase.DocumentTypeUseCase;
import com.elogix.api.customers.domain.usecase.MembershipUseCase;
import com.elogix.api.customers.infrastructure.helpers.mappers.CustomerMapper;
import com.elogix.api.generics.infrastructure.repository.GenericNamed.GenericNamedGatewayImpl;
import com.elogix.api.shared.domain.model.Hits;
import com.elogix.api.shared.infraestructure.helpers.UpdateUtils;

import jakarta.persistence.EntityManager;

public class CustomerGatewayImpl
        extends GenericNamedGatewayImpl<Customer, CustomerData, CustomerDataJpaRepository, CustomerMapper>
        implements CustomerGateway {

    private final DocumentTypeUseCase docTypeUseCase;
    private final MembershipUseCase membershipUseCase;
    private final BranchOfficeUseCase officeUseCase;

    public CustomerGatewayImpl(
            CustomerDataJpaRepository repository,
            CustomerMapper mapper,
            DocumentTypeUseCase docTypeUseCase,
            MembershipUseCase membershipUseCase,
            BranchOfficeUseCase officeUseCase,
            EntityManager entityManager,
            UpdateUtils updateUtils,
            String deletedFilter) {
        super(repository, mapper, entityManager, updateUtils, deletedFilter);
        this.docTypeUseCase = docTypeUseCase;
        this.membershipUseCase = membershipUseCase;
        this.officeUseCase = officeUseCase;
    }

    // TODO add the logic for a new Customer

    @Override
    public Customer update(Customer customer) {
        validateCustomer(customer);
        Customer existing = findById(customer.getId(), false);

        updateBasicInfo(customer, existing);
        updateMembership(customer, existing);
        updateBranchOffices(customer, existing);

        return save(existing);
    }

    private void validateCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Customer and customer ID cannot be null");
        }
    }

    private void updateBasicInfo(Customer customer, Customer existing) {
        if (customer.getName() != null) {
            updateUtils.updateIfNotEmptyAndNotEqual(
                    customer.getName().toUpperCase(),
                    existing::setName,
                    existing.getName());
        }

        if (customer.getPhone() != null) {
            updateUtils.updateIfNotEmptyAndNotEqual(
                    customer.getPhone(),
                    existing::setPhone,
                    existing.getPhone());
        }

        if (customer.getEmail() != null) {
            updateUtils.updateIfNotEmptyAndNotEqual(
                    customer.getEmail().toLowerCase(),
                    existing::setEmail,
                    existing.getEmail());
        }

        if (customer.getDocumentType() != null) {
            DocumentType managedDocType = docTypeUseCase.findById(customer.getDocumentType().getId(), false);
            existing.setDocumentType(managedDocType);
        }
    }

    private void updateMembership(Customer customer, Customer existing) {
        if (customer.getMembership() == null
                || customer.getMembership().getId() == null
                || customer.getMembership().getId() == -1) {
            existing.setMembership(null);
        } else {
            Membership managedMembership = membershipUseCase.findById(customer.getMembership().getId(), false);
            existing.setMembership(managedMembership);
        }
    }

    private void updateBranchOffices(Customer customer, Customer existing) {
        if (customer.getBranchOfficeList() == null) {
            return;
        }

        for (BranchOffice branchOffice : customer.getBranchOfficeList()) {
            processIndividualBranchOffice(branchOffice, customer, existing);
        }
    }

    private void processIndividualBranchOffice(BranchOffice branchOffice, Customer customer, Customer existing) {
        BranchOffice managedBranchOffice;

        if (branchOffice.getId() != null) {
            managedBranchOffice = processExistingBranchOffice(branchOffice, customer, existing);
        } else if (branchOffice.getAddress() != null) {
            managedBranchOffice = processNewBranchOffice(branchOffice);
        } else {
            return;
        }

        customer.getBranchOfficeList().add(managedBranchOffice);
    }

    private BranchOffice processExistingBranchOffice(BranchOffice branchOffice, Customer customer, Customer existing) {
        if (!existing.getBranchOfficeList().stream()
                .anyMatch(bo -> bo.getId().equals(branchOffice.getId()))) {
            return officeUseCase.findById(branchOffice.getId(), false);
        }

        customer.getBranchOfficeList().remove(branchOffice);
        return existing.getBranchOfficeList().stream()
                .filter(bo -> bo.getId().equals(branchOffice.getId()))
                .findFirst()
                .orElseThrow();
    }

    private BranchOffice processNewBranchOffice(BranchOffice branchOffice) {
        try {
            return officeUseCase.findByAddress(branchOffice.getAddress(), false);
        } catch (NoSuchElementException e) {
            return officeUseCase.add(branchOffice);
        }
    }

    @Override
    public void deleteByDocumentNumber(String documentNumber) {
        repository.deleteByDocumentNumber(documentNumber);
    }

    @Override
    public Customer findByEmail(String email, boolean isDeleted) {
        Optional<CustomerData> customerData;
        try (Session session = setDeleteFilter(isDeleted)) {
            customerData = repository.findByEmail(email);
            session.disableFilter(this.deletedFilter);
        }

        return mapper.toDomain(customerData.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Customer findByDocumentNumber(String documentNumber, boolean isDeleted) {
        Optional<CustomerData> customerData;
        try (Session session = setDeleteFilter(isDeleted)) {
            customerData = repository.findByDocumentNumber(documentNumber);
            session.disableFilter(this.deletedFilter);
        }
        return mapper.toDomain(customerData.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public int updateHits(Hits hits) throws IllegalArgumentException, NoSuchElementException {
        return repository.updateHits(hits.getId(), hits.getAmount());
    }

    @Override
    public int incrementHits(Hits hits) throws IllegalArgumentException, NoSuchElementException {
        Customer customer = mapper
                .toDomain(((CrudRepository<CustomerData, Long>) repository).findById(hits.getId()).orElseThrow());
        if (customer != null && customer.getId() != null) {
            Long amount = customer.getHits();
            if (hits.isIncrease()) {
                amount++;
            } else {
                amount--;
            }
            return repository.updateHits(hits.getId(), amount);
        }
        return 0;
    }

    @Override
    public void incrementOneHit(Long id) {
        Hits hits = Hits.builder().id(id).amount(1L).isIncrease(true).build();
        this.incrementHits(hits);
    }
}

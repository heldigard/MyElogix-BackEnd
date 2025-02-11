package com.tarapaca.api.customers.infrastructure.helpers.mappers;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.customers.domain.model.BranchOffice;
import com.tarapaca.api.customers.domain.model.Customer;
import com.tarapaca.api.customers.infrastructure.driven_adapters.jpa_repository.customer.CustomerData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class CustomerMapper extends GenericNamedMapper<Customer, CustomerData> {
    private final DocumentTypeMapper documentTypeMapper;
    private final BranchOfficeMapper branchOfficeMapper;
    private final MembershipMapper membershipMapper;

    public CustomerMapper(DocumentTypeMapper documentTypeMapper,
                         BranchOfficeMapper branchOfficeMapper,
                         MembershipMapper membershipMapper,
                         UserBasicMapper userMapper) {
        super(Customer.class, CustomerData.class, userMapper);
        this.documentTypeMapper = documentTypeMapper;
        this.branchOfficeMapper = branchOfficeMapper;
        this.membershipMapper = membershipMapper;
    }

    @Override
    @Nullable
    public Customer toDomain(@Nullable CustomerData source, @NonNull Customer target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getDocumentNumber()).ifPresent(target::setDocumentNumber);
        Optional.ofNullable(source.getDocumentType()).ifPresent(documentType ->
        target.setDocumentType(documentTypeMapper.toDomain(documentType)));
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getHits()).ifPresent(target::setHits);
        target.setActive(source.isActive());

        Optional.ofNullable(source.getMembership()).ifPresent(membership ->
        target.setMembership(membershipMapper.toDomain(membership)));

        Set<BranchOffice> branchOffices = Optional.ofNullable(source.getBranchOfficeList())
                .orElse(Collections.emptySet())
                .stream()
                        .map(branchOfficeMapper::toDomain)
                .collect(Collectors.toSet());
        target.setBranchOfficeList(branchOffices);

        return target;
    }

    @Override
    @Nullable
    public CustomerData toData(@Nullable Customer source, @NonNull CustomerData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getDocumentNumber()).ifPresent(target::setDocumentNumber);
        Optional.ofNullable(source.getDocumentType()).ifPresent(documentType ->
                target.setDocumentType(documentTypeMapper.toData(documentType)));
        Optional.ofNullable(source.getEmail()).ifPresent(target::setEmail);
        Optional.ofNullable(source.getPhone()).ifPresent(target::setPhone);
        Optional.ofNullable(source.getHits()).ifPresent(target::setHits);
        target.setActive(source.isActive());

        Optional.ofNullable(source.getMembership()).ifPresent(membership ->
                target.setMembership(membershipMapper.toData(membership)));

        Set<BranchOffice> branchOffices = Optional.ofNullable(source.getBranchOfficeList())
                .orElse(Collections.emptySet());
        target.setBranchOfficeList(branchOffices.stream()
                .map(branchOfficeMapper::toData)
                .collect(Collectors.toSet()));

        return target;
    }
}

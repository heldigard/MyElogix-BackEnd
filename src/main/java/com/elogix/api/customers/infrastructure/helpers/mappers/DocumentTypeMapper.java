package com.elogix.api.customers.infrastructure.helpers.mappers;

import org.springframework.stereotype.Component;

import com.elogix.api.customers.domain.model.DocumentType;
import com.elogix.api.customers.infrastructure.driven_adapters.jpa_repository.document_type.DocumentTypeData;
import com.elogix.api.generics.infrastructure.helpers.GenericNamedMapper;
import com.elogix.api.users.infrastructure.helpers.mappers.UserBasicMapper;

@Component
public class DocumentTypeMapper extends GenericNamedMapper<DocumentType, DocumentTypeData> {

    public DocumentTypeMapper(UserBasicMapper userMapper) {
        super(DocumentType.class, DocumentTypeData.class, userMapper);
    }
}

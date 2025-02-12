package com.elogix.api.customers.application.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.elogix.api.customers.domain.model.EMembership;

import java.io.IOException;

public class EMembershipDeserializer extends JsonDeserializer<EMembership> {
    @Override
    public EMembership deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return EMembership.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

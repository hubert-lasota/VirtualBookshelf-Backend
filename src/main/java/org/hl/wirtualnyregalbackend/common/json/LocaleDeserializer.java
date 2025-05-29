package org.hl.wirtualnyregalbackend.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.hl.wirtualnyregalbackend.common.exception.InvalidFieldsException;
import org.hl.wirtualnyregalbackend.common.model.ApiError;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocaleDeserializer extends JsonDeserializer<Locale> {

    @Override
    public Locale deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return Locale.forLanguageTag(p.getText());
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError(p.currentName(), e.getMessage());
            throw new InvalidFieldsException(List.of(error));
        }

    }

}

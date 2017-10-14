package com.example.restservice.model.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.LocalDate;

import java.io.IOException;

/**
 * Custom Local Date Deserialzer to convert a String date to Local Date
 * Created by melvin on 10/12/17.
 */
public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected CustomLocalDateDeserializer(Class<LocalDate> localDate) {
        super(localDate);
    }

    public CustomLocalDateDeserializer() {
        this(null);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        return new LocalDate(jsonParser.getText());
    }
}

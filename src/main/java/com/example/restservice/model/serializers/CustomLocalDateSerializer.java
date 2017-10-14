package com.example.restservice.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.LocalDate;
import java.io.IOException;

/**
 * Custom date serializer to return json output in yyyy-MM-dd format.
 * Created by melvin on 10/12/17.
 */
public class CustomLocalDateSerializer extends StdSerializer<LocalDate> {

    public CustomLocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    protected CustomLocalDateSerializer() {
        this(null);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDate.toString("yyyy-MM-dd"));
    }
}

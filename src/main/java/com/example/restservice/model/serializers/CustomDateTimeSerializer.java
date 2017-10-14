package com.example.restservice.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * Custom date time serializer to format date to ISO date format
 * Created by melvin on 10/12/17.
 */
public class CustomDateTimeSerializer extends StdSerializer<DateTime> {

    public CustomDateTimeSerializer(Class<DateTime> t) {
        super(t);
    }

    protected CustomDateTimeSerializer() {
        this(null);
    }

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(dateTime.toString("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }
}

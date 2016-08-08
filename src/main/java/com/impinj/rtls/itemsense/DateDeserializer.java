package com.impinj.rtls.itemsense;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ralemy on 8/7/16.
 */
public class DateDeserializer extends StdDeserializer<Date> {

    private SimpleDateFormat formatter;

    public DateDeserializer(Class<?> vc) {
        super(vc);
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    public DateDeserializer(){
        this(null);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            String date = jsonParser.getText().substring(0, 24);
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}

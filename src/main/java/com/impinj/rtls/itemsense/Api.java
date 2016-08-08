package com.impinj.rtls.itemsense;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.typesafe.config.ConfigException;
import hello.Application;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ralemy on 8/6/16.
 * Singleton Object proxy for ItemSense calls.
 */
public class Api {

    static String REGISTER_QUEUE = "/itemsense/data/v1/messageQueues/zoneTransition/configure";
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private RestTemplate restTemplate;
    private String user;
    private String pass;
    private String base;

    public Api(String url, String username, String password) {
        user = username;
        pass = password;
        base = url;
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    }

    public <T, U> T post(String endpoint, Class<T> cls, U payload) throws HttpStatusCodeException {
        log.info("payload" + payload.toString());
        HttpEntity<U> entity = new HttpEntity<U>(payload, getHeader());
        ResponseEntity<T> result = restTemplate.exchange(base + endpoint, HttpMethod.POST, entity, cls);
        if (result.getStatusCode().value() > 399)
            throw new HttpClientErrorException(result.getStatusCode(), result.getBody().toString());
        return result.getBody();
    }

    private HttpHeaders getHeader() {
        return setJson(authenticate(new HttpHeaders()));
    }

    private HttpHeaders setJson(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders authenticate(HttpHeaders headers) {
        headers.set("Authorization", "Basic " + authorizationString());
        return headers;
    }

    private String authorizationString() {
        return new String(Base64.encodeBase64((user + ":" + pass).getBytes()));
    }

    public class dateSerializer extends JsonSerializer<Date> {

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeString(formatter.format(date) + "Z[Etc/UTC]");
        }
    }
}


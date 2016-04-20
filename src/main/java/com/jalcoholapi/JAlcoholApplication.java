package com.jalcoholapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import java.io.IOException;

/**
 * Created by weiliyang on 7/24/15.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class JAlcoholApplication extends SpringBootServletInitializer {

    private final static Logger logger = LoggerFactory.getLogger(JAlcoholApplication.class);

    public static void main(String[] args) {
        final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper objectMapper
                    = jacksonObjectMapper;

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return objectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return objectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        SpringApplication.run(JAlcoholApplication.class, args);
        logger.info("JAlcohol API application started.");
    }
}

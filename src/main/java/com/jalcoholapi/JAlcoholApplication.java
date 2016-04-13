package com.jalcoholapi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Created by weiliyang on 7/24/15.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class JAlcoholApplication extends SpringBootServletInitializer {

    private final static Logger logger = LoggerFactory.getLogger(JAlcoholApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JAlcoholApplication.class, args);
        logger.info("JAlcohol API application started.");
    }
}

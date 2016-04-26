package com.jalcoholapi.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by weiliyang on 4/21/16.
 */
@ConfigurationProperties
@Service
public class CodoonService {

    private final static Logger logger = LoggerFactory.getLogger(CodoonService.class);

    @Value("${app.config.codoon.apiBaseURL}")
    private String codoonApiBaseURL;

    public <T> T callAPI(String method, String api, String nekot, Class<T> clazz) {
        return callAPI(method, api, null, nekot, clazz);
    }

    public <T> T callAPI(String method, String api, String data, String nekot, Class<T> clazz) {
        String apiURL = codoonApiBaseURL + api;

        T genericResponse = null;

        if (StringUtils.isNotBlank(nekot)) {
            HttpRequest request = null;
            if ("get".equals(method)) {
                request = Unirest.get(apiURL);
            } else if ("post".equals(method)) {
                HttpRequestWithBody requestWithBody = Unirest.post(apiURL);
                if (data != null) {
                    requestWithBody.body(data);
                }
                request = requestWithBody;
            }

            if (request != null) {
                HttpResponse<T> response = null;
                try {
                    response = request
                            .header("accept", "application/json")
                            .header("Authorization", "Bearer " + nekot)
                            .asObject(clazz);
                } catch (UnirestException e) {
                    logger.error(e.getMessage(), e);
                }
                genericResponse = response.getBody();
            }
        }

        return genericResponse;
    }
}

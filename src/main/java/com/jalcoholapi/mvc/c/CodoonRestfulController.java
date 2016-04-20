package com.jalcoholapi.mvc.c;

import com.jalcoholapi.mvc.m.CodoonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Hashtable;

/**
 * Created by weiliyang on 7/24/15.
 */
@ConfigurationProperties
@RestController
@RequestMapping("/api/codoon/")
public class CodoonRestfulController {

    private final static Logger logger = LoggerFactory.getLogger(CodoonRestfulController.class);

    @Value("${app.config.codoon.apiBaseURL}")
    private String codoonApiBaseURL;

    @RequestMapping(value = "/call")
    public Object call(@RequestParam String method, @RequestParam String api, HttpSession session) throws Exception {

        String apiURL = codoonApiBaseURL + api;

        JsonNode responseJsonNode = null;
        if (session.getAttribute("codoonToken") != null) {
            String accessToken = ((CodoonToken) session.getAttribute("codoonToken")).accessToken;
            if (StringUtils.isNotBlank(accessToken)) {
                HttpRequest request = null;
                if ("get".equals(method)) {
                    request = Unirest.get(apiURL);
                } else if ("post".equals(method)) {
                    request = Unirest.post(apiURL);
                }

                if (request != null) {
                    HttpResponse<JsonNode> response = request
                            .header("accept", "application/json")
                            .header("Authorization", "Bearer " + accessToken)
                            .asJson();
                    responseJsonNode = response.getBody();
                }
            }
        }

        return responseJsonNode == null ? "" : responseJsonNode.toString();
    }
}

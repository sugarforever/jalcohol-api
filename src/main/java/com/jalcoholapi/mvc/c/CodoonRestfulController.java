package com.jalcoholapi.mvc.c;

import com.jalcoholapi.mvc.m.CodoonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    @Value("${app.config.codoon.appKey}")
    private String codoonAppKey;
    @Value("${app.config.codoon.appSecret}")
    private String codoonAppSecret;
    @Value("${app.config.codoon.authorizeURL}")
    private String codoonAuthorizeURL;
    @Value("${app.config.codoon.redirectURL}")
    private String codoonRedirectURL;
    @Value("${app.config.codoon.accessTokenURL}")
    private String codoonAccessTokenURL;

    @RequestMapping(value = "/token")
    public Object getToken(HttpSession session) {
        return session.getAttribute("codoonToken");
    }

    @RequestMapping(value = "/call", method = RequestMethod.POST)
    public Object postCall(@RequestParam String method, @RequestParam String api, @RequestBody(required = false) String data, @RequestParam String nekot) throws Exception {
        String apiURL = codoonApiBaseURL + api;

        JsonNode responseJsonNode = null;

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
                HttpResponse<JsonNode> response = request
                        .header("accept", "application/json")
                        .header("Authorization", "Bearer " + nekot)
                        .asJson();
                responseJsonNode = response.getBody();
            }
        }

        return responseJsonNode == null ? "" : responseJsonNode.toString();
    }

    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public Object getCall(@RequestParam String method, @RequestParam String api, @RequestParam String nekot) throws Exception {
        String apiURL = codoonApiBaseURL + api;

        JsonNode responseJsonNode = null;

        if (StringUtils.isNotBlank(nekot)) {
            HttpRequest request = null;
            if ("get".equals(method)) {
                request = Unirest.get(apiURL);
            } else if ("post".equals(method)) {
                request = Unirest.post(apiURL);
            }

            if (request != null) {
                HttpResponse<JsonNode> response = request
                        .header("accept", "application/json")
                        .header("Authorization", "Bearer " + nekot)
                        .asJson();
                responseJsonNode = response.getBody();
            }
        }

        return responseJsonNode == null ? "" : responseJsonNode.toString();
    }

    @RequestMapping(value = "/refresh-token/{refreshToken}/")
    public Object refreshToken(@PathVariable String refreshToken, HttpSession session) throws Exception {
        HttpResponse<CodoonToken> response = Unirest.post(codoonAccessTokenURL)
                .header("accept", "application/json")
                .basicAuth(codoonAppKey, codoonAppSecret)
                .queryString("client_id", codoonAppKey)
                .queryString("grant_type", "refresh_token")
                .queryString("refresh_token", refreshToken)
                .queryString("scope", "user,sports")
                .asObject(CodoonToken.class);
        CodoonToken codoonToken = response.getBody();

        if (codoonToken == null || codoonToken.hasError()) {
            session.removeAttribute("codoonToken");
        } else {
            session.setAttribute("codoonToken", codoonToken);
        }

        return codoonToken;
    }
}

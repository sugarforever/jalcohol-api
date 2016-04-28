package com.jalcoholapi.service;

import com.jalcoholapi.mvc.m.CodoonToken;
import com.jalcoholapi.mvc.m.CodoonUserInfo;
import com.jalcoholapi.persistence.model.UserToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserTokenService userTokenService;

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

    public UserToken refreshToken(String refreshToken) {
        UserToken userToken = null;

        try {
            HttpResponse<CodoonToken> response = Unirest.post(codoonAccessTokenURL)
                    .header("accept", "application/json")
                    .basicAuth(codoonAppKey, codoonAppSecret)
                    .queryString("client_id", codoonAppKey)
                    .queryString("grant_type", "refresh_token")
                    .queryString("refresh_token", refreshToken)
                    .queryString("scope", "user,sports")
                    .asObject(CodoonToken.class);
            CodoonToken codoonToken = response.getBody();

            if (codoonToken != null) {
                CodoonUserInfo codoonUserInfo = callAPI("get", "verify_credentials", null, codoonToken.accessToken, CodoonUserInfo.class);
                userToken = userTokenService.save(codoonToken, codoonUserInfo);
            }
        } catch (UnirestException e) {
            logger.error(e.getMessage(), e);
        }
        return userToken;
    }
}

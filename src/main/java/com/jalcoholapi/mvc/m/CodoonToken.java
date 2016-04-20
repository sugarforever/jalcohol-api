package com.jalcoholapi.mvc.m;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by weiliyang on 4/20/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodoonToken {

    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("refresh_token")
    public String refreshToken;
    @JsonProperty("user_id")
    public String userId;
    public String scope;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expire_in")
    public Long expireIn;
    public Long timestamp;

    @JsonProperty("error_description")
    public String errorDescription;
    @JsonProperty("error_code")
    public Integer errorCode;
    public String error;

    public boolean hasError() {
        return errorCode != null;
    }
}

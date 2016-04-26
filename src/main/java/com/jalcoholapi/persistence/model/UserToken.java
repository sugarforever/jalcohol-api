package com.jalcoholapi.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by weiliyang on 4/21/16.
 */
@Entity
@Table(name = "t_user_token")
public class UserToken {

    @Id
    private String userId;
    private String userNick;

    private String accessToken;
    private String refreshToken;

    private String scope;
    private String tokenType;
    private Long expireIn;
    private Long timestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(final String userNick) {
        this.userNick = userNick;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(final Long expireIn) {
        this.expireIn = expireIn;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }
}

package com.jalcoholapi.service;

import com.jalcoholapi.mvc.m.CodoonToken;
import com.jalcoholapi.mvc.m.CodoonUserInfo;
import com.jalcoholapi.persistence.model.UserToken;
import com.jalcoholapi.persistence.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by weiliyang on 4/21/16.
 */
@Service
public class UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken save(CodoonToken codoonToken, CodoonUserInfo codoonUserInfo) {
        UserToken userToken = null;
        if (codoonToken != null && codoonUserInfo != null) {
            userToken = new UserToken();
            userToken.setAccessToken(codoonToken.accessToken);
            userToken.setExpireIn(codoonToken.expireIn);
            userToken.setRefreshToken(codoonToken.refreshToken);
            userToken.setScope(codoonToken.scope);
            userToken.setTimestamp(codoonToken.timestamp);
            userToken.setTokenType(codoonToken.tokenType);
            userToken.setUserId(codoonToken.userId);
            userToken.setUserNick(codoonUserInfo.nick);

            userToken = userTokenRepository.save(userToken);
        }

        return userToken;
    }

    public UserToken findByUserId(String userId) {
        return userTokenRepository.findByUserId(userId);
    }
}

package com.jalcoholapi.service;

import com.jalcoholapi.mvc.m.CodoonNotificationForm;
import com.jalcoholapi.persistence.model.CodoonNotification;
import com.jalcoholapi.persistence.model.UserToken;
import com.jalcoholapi.persistence.repository.CodoonNotificationRepository;
import com.jalcoholapi.persistence.repository.UserTokenRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiliyang on 4/21/16.
 */
@Service
public class CodoonNotificationService {

    private final static Logger logger = LoggerFactory.getLogger(CodoonNotificationService.class);

    @Autowired
    private CodoonNotificationRepository codoonNotificationRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private CodoonService codoonService;

    public List<CodoonNotification> findAllCodoonNotificationsByUserId(String userId) {
        List<CodoonNotification> notifications = new ArrayList<CodoonNotification>();

        if (StringUtils.isNotEmpty(userId)) {
            Collection<CodoonNotification> c = codoonNotificationRepository.findAllByUserId(userId);
            if (c.size() > 0) {
                notifications.addAll(c);
            }
        }
        return notifications;
    }

    public CodoonNotification save(CodoonNotificationForm codoonNotificationForm) {
        CodoonNotification notification = null;
        if (codoonNotificationForm != null && StringUtils.isNotEmpty(codoonNotificationForm.getResource_id())) {
            notification = new CodoonNotification();

            notification.setResourceId(codoonNotificationForm.getResource_id());
            notification.setCatalog(codoonNotificationForm.getCatalog());
            notification.setEndTime(codoonNotificationForm.getEnd_time());
            notification.setStartTime(codoonNotificationForm.getStart_time());
            notification.setUserId(codoonNotificationForm.getUser_id());
            notification.setLocal(false);

            CodoonNotification c = codoonNotificationRepository.findByResourceId(codoonNotificationForm.getResource_id());
            if (c != null) {
                logger.warn("Codoon notification already exists.");
                notification = c;
            } else {
                notification = codoonNotificationRepository.save(notification);
                fetchRoutesData(notification);
            }
        }

        return notification;
    }

    private void fetchRoutesData(final CodoonNotification codoonNotification) {
        if (codoonNotification != null && "gps_sports".equals(codoonNotification.getCatalog())) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                UserToken userToken = userTokenRepository.findByUserId(codoonNotification.getUserId());
                userToken = codoonService.refreshToken(userToken.getRefreshToken());
                String data = String.format("\"route_id\": \"%s\"", codoonNotification.getResourceId());
                Object obj = codoonService.callAPI("post", "get_route_by_id", data, userToken.getAccessToken(), Object.class);
            });
        }
    }
}

package com.jalcoholapi.service;

import com.jalcoholapi.mvc.m.CodoonNotificationForm;
import com.jalcoholapi.persistence.model.CodoonNotification;
import com.jalcoholapi.persistence.repository.CodoonNotificationRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by weiliyang on 4/21/16.
 */
@Service
public class CodoonNotificationService {

    private final static Logger logger = LoggerFactory.getLogger(CodoonNotificationService.class);

    @Autowired
    private CodoonNotificationRepository codoonNotificationRepository;

    public CodoonNotification save(CodoonNotificationForm codoonNotificationForm) {
        CodoonNotification notification = null;
        if (codoonNotificationForm != null && StringUtils.isNotEmpty(codoonNotificationForm.getResource_id())) {
            Long resourceId = null;

            try {
                resourceId = Long.parseLong(codoonNotificationForm.getResource_id());
            } catch (NumberFormatException ex) {
                logger.error(ex.getMessage(), ex);
                return notification;
            }

            notification = new CodoonNotification();

            notification.setResourceId(resourceId);
            notification.setCatalog(codoonNotificationForm.getCatalog());
            notification.setEndTime(codoonNotificationForm.getEnd_time());
            notification.setStartTime(codoonNotificationForm.getStart_time());
            notification.setUserId(codoonNotificationForm.getUser_id());

            CodoonNotification c = codoonNotificationRepository.findOne(resourceId);
            if (c != null) {
                logger.warn("Codoon notification already exists.");
                notification = c;
            } else {
                notification = codoonNotificationRepository.save(notification);
            }
        }

        return notification;
    }
}

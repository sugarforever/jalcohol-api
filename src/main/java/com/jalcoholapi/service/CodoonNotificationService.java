package com.jalcoholapi.service;

import com.jalcoholapi.mvc.m.CodoonNotificationForm;
import com.jalcoholapi.persistence.model.CodoonNotification;
import com.jalcoholapi.persistence.repository.CodoonNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
        if (codoonNotificationForm != null) {
            notification = new CodoonNotification();
            CodoonNotification.CodoonNotificationPK pk = new CodoonNotification.CodoonNotificationPK();
            pk.setCatalog(codoonNotificationForm.getCatalog());
            pk.setEndTime(codoonNotificationForm.getEnd_time());
            pk.setResourceId(codoonNotificationForm.getResource_id());
            pk.setStartTime(codoonNotificationForm.getStart_time());
            pk.setUserId(codoonNotificationForm.getUser_id());
            notification.setCodoonNotificationPK(pk);

            List<CodoonNotification> c = codoonNotificationRepository.findByCodoonNotificationPK(pk);
            if (c.size() > 0) {
                logger.warn("Codoon notification already exists.");
                notification = c.get(0);
            } else {
                notification = codoonNotificationRepository.save(notification);
            }
        }

        return notification;
    }
}

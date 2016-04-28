package com.jalcoholapi.persistence.repository;

import com.jalcoholapi.persistence.model.CodoonNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Created by weiliyang on 7/24/15.
 */
@Transactional
@Repository
public interface CodoonNotificationRepository extends JpaRepository<CodoonNotification, Long> {

    public CodoonNotification findByResourceId(String resourceId);

    public Collection<CodoonNotification> findAllByUserId(String userId);
}

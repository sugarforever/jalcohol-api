package com.jalcoholapi.persistence.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by weiliyang on 4/22/16.
 */
@Entity
@Table(name = "t_codoon_notification")
public class CodoonNotification {

    @Id
    private Long resourceId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String catalog;
    @Column(nullable = true)
    private String startTime;
    @Column(nullable = true)
    private String endTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(final String catalog) {
        this.catalog = catalog;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(final Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
}

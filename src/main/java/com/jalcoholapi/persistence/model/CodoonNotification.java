package com.jalcoholapi.persistence.model;

import javax.persistence.*;

/**
 * Created by weiliyang on 4/22/16.
 */
@Entity
@Table(name = "t_codoon_notification")
public class CodoonNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String resourceId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String catalog;
    @Column(nullable = true)
    private String startTime;
    @Column(nullable = true)
    private String endTime;
    @Column(nullable = false)
    private Boolean local;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(final String resourceId) {
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

    public Boolean getLocal() {
        return local;
    }

    public void setLocal(final Boolean local) {
        this.local = local;
    }
}

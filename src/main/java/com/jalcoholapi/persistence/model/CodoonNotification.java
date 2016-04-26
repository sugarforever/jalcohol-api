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

    @EmbeddedId
    private CodoonNotificationPK codoonNotificationPK;

    public CodoonNotificationPK getCodoonNotificationPK() {
        return codoonNotificationPK;
    }

    public void setCodoonNotificationPK(final CodoonNotificationPK codoonNotificationPK) {
        this.codoonNotificationPK = codoonNotificationPK;
    }

    @Embeddable
    public static class CodoonNotificationPK implements Serializable {

        @Column(nullable = false)
        private String userId;
        @Column(nullable = false)
        private String catalog;
        @Column(nullable = false)
        private String resourceId;
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

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(userId).append(catalog).append(resourceId).append(startTime).append(endTime).toHashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof CodoonNotificationPK) {
                final CodoonNotificationPK that = (CodoonNotificationPK) obj;
                return new EqualsBuilder().append(this.userId, that.userId).append(this.catalog, that.catalog).append(this.resourceId, that.resourceId).append(this.startTime, that.startTime).append(this.endTime, that.endTime).isEquals();
            } else {
                return false;
            }
        }
    }
}

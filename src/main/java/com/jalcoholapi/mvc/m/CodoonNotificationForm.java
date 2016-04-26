package com.jalcoholapi.mvc.m;

/**
 * Created by weiliyang on 4/22/16.
 */
public class CodoonNotificationForm {


    private String user_id;
    private String catalog;
    private String resource_id;
    private String start_time;
    private String end_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(final String user_id) {
        this.user_id = user_id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(final String catalog) {
        this.catalog = catalog;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(final String resource_id) {
        this.resource_id = resource_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(final String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(final String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("user_id: " + String.valueOf(user_id) + "; ");
        buffer.append("catalog: " + String.valueOf(catalog) + "; ");
        buffer.append("resource_id: " + String.valueOf(resource_id) + "; ");
        buffer.append("start_time: " + String.valueOf(start_time) + "; ");
        buffer.append("end_time: " + String.valueOf(end_time));

        return buffer.toString();
    }
}

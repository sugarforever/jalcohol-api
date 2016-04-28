package com.jalcoholapi.mvc.m;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by weiliyang on 4/20/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodoonRoute {

    public String status;
    public String description;
    public Data data;

    public static class Data {
        @JsonProperty("total_time")
        public Integer totalTime;
        @JsonProperty("upload_time")
        public String uploadTime;
        @JsonProperty("total_calories")
        public Float totalCalories;
        @JsonProperty("start_time")
        public String startTime;
        @JsonProperty("sports_type")
        public String sportsType;
        @JsonProperty("route_image")
        public String routeImage;
        @JsonProperty("activity_result")
        public Integer activityResult;
        @JsonProperty("end_time")
        public String endTime;
        @JsonProperty("total_length")
        public Float totalLength;
        @JsonProperty("activity_type")
        public String activityType;
        @JsonProperty("goal_type")
        public String goalType;
        @JsonProperty("goal_value")
        public Float goalValue;

        @JsonProperty("error_description")
        public String errorDescription;
        @JsonProperty("error_code")
        public Integer errorCode;
        public String error;

        public boolean hasError() {
            return errorCode != null;
        }
    }
}

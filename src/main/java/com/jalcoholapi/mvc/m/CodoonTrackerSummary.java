package com.jalcoholapi.mvc.m;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by weiliyang on 4/20/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodoonTrackerSummary {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class Tracker {
        public String date;
        public Integer activity;
        public Integer minutes;
        public Integer calories;
        public Integer steps;
        public String status;
        public Integer meters;

        public String getDate() {
            return date;
        }

        public Integer getActivity() {
            return activity;
        }

        public Integer getMinutes() {
            return minutes;
        }

        public Integer getCalories() {
            return calories;
        }

        public Integer getSteps() {
            return steps;
        }

        public String getStatus() {
            return status;
        }

        public Integer getMeters() {
            return meters;
        }
    }

    @JsonProperty("data")
    public Tracker[] trackers;

    public Tracker[] getTrackers() {
        return trackers;
    }
}

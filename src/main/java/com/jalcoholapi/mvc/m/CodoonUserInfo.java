package com.jalcoholapi.mvc.m;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by weiliyang on 4/20/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodoonUserInfo {
    public String nick;
    public String gender;
    public String email;
    public Integer age;
}

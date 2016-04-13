package com.jalcoholapi.mvc.c;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;

/**
 * Created by weiliyang on 7/24/15.
 */
@RestController
@RequestMapping("/codoon")
public class CodoonRestfulController {

    private final static Logger logger = LoggerFactory.getLogger(CodoonRestfulController.class);

    @RequestMapping("/get_code/")
    public Object getCode(@RequestParam String code) {
        Hashtable hash = new Hashtable();
        hash.put("code", code);
        return hash;
    }
}

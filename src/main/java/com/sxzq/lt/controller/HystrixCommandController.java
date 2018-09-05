package com.sxzq.lt.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liutao on 2018/9/3.
 */
@RestController
public class HystrixCommandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixCommandController.class);

    @RequestMapping("/hystrixFallBack")
    public String hystrixFallBack() {
        LOGGER.error("触发了断路由.");
        return "hystrix gateway fall back";
    }

//    @HystrixCommand(commandKey="authHystrixCommand")
//    public String authHystrixCommand() {
//        return "authHystrixCommand";
//    }
}

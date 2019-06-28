package com.clei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryConfigController {
    @Autowired
    private EurekaClientConfigBean eurekaClientConfigBean;

    @GetMapping("/eureka-server")
    public Object get(){
        return eurekaClientConfigBean.getServiceUrl();
    }
}

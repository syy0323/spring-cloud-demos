package cn.syy.spring_cloud_eureka_consumer_feign.controller;

import cn.syy.spring_cloud_eureka_consumer_feign.service.HelloFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloFeign helloFeign;

    @GetMapping("/hello")
    public String hello() {

        return helloFeign.hello();
    }
}

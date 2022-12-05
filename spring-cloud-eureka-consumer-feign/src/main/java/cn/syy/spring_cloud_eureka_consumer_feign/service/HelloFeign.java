package cn.syy.spring_cloud_eureka_consumer_feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "EUREKA-CLIENT")
public interface HelloFeign {

    @GetMapping("/hello")
    String hello();
}

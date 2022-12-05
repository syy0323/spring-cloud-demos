package cn.syy.spring_cloud_eureka_cosumer_ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/hello")
    public String hello() {

        ResponseEntity<String> entity = restTemplate.getForEntity("http://EUREKA-CLIENT/hello", String.class);

        return entity.getBody();
    }

}

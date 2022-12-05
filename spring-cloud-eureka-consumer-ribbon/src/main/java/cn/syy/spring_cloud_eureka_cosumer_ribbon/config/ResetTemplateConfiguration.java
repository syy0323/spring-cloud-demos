package cn.syy.spring_cloud_eureka_cosumer_ribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResetTemplateConfiguration {

    @Bean
    @LoadBalanced //开启ribbon自带负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

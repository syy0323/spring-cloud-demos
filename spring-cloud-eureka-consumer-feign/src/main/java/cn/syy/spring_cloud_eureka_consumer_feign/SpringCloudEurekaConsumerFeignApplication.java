package cn.syy.spring_cloud_eureka_consumer_feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class SpringCloudEurekaConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaConsumerFeignApplication.class, args);
    }

}

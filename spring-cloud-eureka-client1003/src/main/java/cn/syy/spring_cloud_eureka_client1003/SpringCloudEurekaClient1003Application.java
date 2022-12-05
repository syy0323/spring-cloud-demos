package cn.syy.spring_cloud_eureka_client1003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringCloudEurekaClient1003Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaClient1003Application.class, args);
    }

}

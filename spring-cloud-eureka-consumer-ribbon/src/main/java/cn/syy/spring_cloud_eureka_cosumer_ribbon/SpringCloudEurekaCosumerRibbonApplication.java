package cn.syy.spring_cloud_eureka_cosumer_ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringCloudEurekaCosumerRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaCosumerRibbonApplication.class, args);
    }

}

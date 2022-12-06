[TOC]

## 什么是SpringCloud

> Spring Cloud是一系列框架的有序集合。它利用Spring
> Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、智能路由、消息总线、负载均衡、断路器、数据监控等，都可以用Spring
> Boot的开发风格做到一键启动和部署。Spring Cloud并没有重复制造轮子，它只是将各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring
> Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

## 优点

- 服务拆分粒度更细，有利于资源重复利用，有利于提高开发效率
- 可以更精准的制定优化服务方案，提高系统的可维护性
- 微服务架构采用去中心化思想，服务之间采用Restful等轻量级通讯，比ESB更轻量
- 适用于互联网时代，产品迭代周期更短
- 减轻团队的成本，可以并行开发，不用关注其他人怎么开发

## 缺点

- 微服务过多，治理成本高，不利于系统维护
- 分布式系统开发的成本高，对团队挑战大

本项目实现完整的cloud服务demo,父工程约束项目依赖版本

- spring boot 版本 2.4.4
- spring cloud版本 2020.0.5

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.4</version>
    </parent>
    <groupId>cn.syy</groupId>
    <artifactId>spring-cloud-demos</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-cloud-demos</name>
    <description>spring-cloud-demos</description>
    <packaging>pom</packaging>
    <properties>
        <java.version>1.8</java.version>
        <spring-boot.version>2.4.4</spring-boot.version>
        <spring-cloud.version>2020.0.5</spring-cloud.version>
    </properties>

    <modules>
        <module>spring-cloud-eureka-server</module>
        <module>spring-cloud-eureka-client</module>
        <module>spring-cloud-eureka-client1003</module>
        <module>spring-cloud-eureka-consumer-ribbon</module>
        <module>spring-cloud-eureka-consumer-feign</module>
    </modules>

    <dependencyManagement>
        <!--引入springcloud依赖的-->
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.4.4</version>
            </plugin>
        </plugins>
    </build>

</project>

```

## Spring Cloud Netflix

- Eureka:服务治理组件，包括服务端的注册中心和客户端的服务发现机制。
- Ribbon:负载均衡的服务调用组件，具有多种负载均衡调用策略。
- Hystrix: 服务容错组件，实现了断路器模式，为依赖服务的出错和延迟提供了容错能力。
- Feign: 基于Ribbon和Hystrix的声明式服务调用组件。
- Zuul: Api网关组件，对请求提供路由以及过滤功能。

### Eureka

>
在微服务架构中往往会有一个注册中心，每个微服务都会向注册中心去注册自己的地址及端口信息，注册中心维护着服务名称与服务实例的对应关系。每个微服务都会定时从注册中心获取服务列表，同时汇报自己的运行情况，这样当有的服务需要调用其他服务时，就可以从自己获取到的服务列表中获取实例地址进行调用，Eureka实现了这套服务注册与发现机制。

1: 搭建Eureka Server服务

1.1: 创建 *spring-cloud-eureka-server* 工程

引入依赖
```xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
1.2:启动类增加注解

```java
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServerApplication.class, args);
    }

}
```
1.3:配置文件增加相应的配置

```yaml
server:
  # 服务注册客户端口号
  port: 1000
spring:
  application:
    name: eureka-server
eureka:
  server:
    # 服务端间隔多久进行定期删除的操作，每过一段时间客户端没有响应将被删除
    eviction-interval-timer-in-ms: 10000
    #续约(心跳机制)百分比  规定时间内超过百分之八十五的应用没有和服务端续约  那么Eureka的保护机制将启动不会剔除任何一个客户端
    renewal-percent-threshold: 0.85
  instance:
    # 客户端的主机名
    hostname: localhost
    # 实例名称
    instance-id: ${spring.application.name}:${eureka.instance.hostname}:${server.port}
    # 续约时间 需要小于服务器的定期删除的时间
    lease-renewal-interval-in-seconds: 5
    # 以ip的形式显示服务信息
    prefer-ip-address: true
  client:
    # 是否向服务注册中心注册自己
    register-with-eureka: false
    # 是否检索服务并拉取服务列表到本地缓存
    fetch-registry: true
    #服务注册中心的配置内容，指定服务注册中心的位置
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

    # 从注册中心拉取列表的时间间隔，时间越短 数据越及时
    registry-fetch-interval-seconds: 10

```



1.4:集群部署则需要配置

```yaml
register-with-eureka: true
service-url:
      defaultZone: http://ip1/eureka/,http://ip2/eureka/
```

2:搭建Eureka Client

2.1: 创建 *spring-cloud-eureka-client* 工程

引入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

2.2:启动类增加注解

```java
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServerApplication.class, args);
    }

}
```

2.3:配置文件增加相应的配置

```yaml
server:
  # 服务注册客户端口号
  port: 1001
spring:
  application:
    name: eureka-client
eureka:
  instance:
    # 客户端的主机名
    hostname: localhost
    # 实例名称
    instance-id: ${spring.application.name}:${eureka.instance.hostname}:${server.port}
    # 续约时间 需要小于服务器的定期删除的时间
    lease-renewal-interval-in-seconds: 5
    # 以ip的形式显示服务信息
    prefer-ip-address: true
  client:
    # 是否向服务注册中心注册自己
    register-with-eureka: true
    # 是否检索服务并拉取服务列表到本地缓存
    fetch-registry: true
    #服务注册中心的配置内容，指定服务注册中心的位置
    service-url:
      defaultZone: http://${eureka.instance.hostname}:1000/eureka/

    # 从注册中心拉取列表的时间间隔，时间越短 数据越及时
    registry-fetch-interval-seconds: 10

```



### Ribbon

> 在微服务架构中，很多服务都会部署多个，其他服务去调用该服务的时候，如何保证负载均衡是个不得不去考虑的问题。负载均衡可以增加系统的可用性和扩展性，当我们使用[RestTemplate](https://so.csdn.net/so/search?q=RestTemplate&spm=1001.2101.3001.7020)来调用其他服务时，Ribbon可以很方便的实现负载均衡功能。

```java
@Configuration
public class ResetTemplateConfiguration {

    @Bean
    @LoadBalanced //开启ribbon自带负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

### Hystrix

> 在微服务架构中，服务与服务之间通过远程调用的方式进行通信，一旦某个被调用的服务发生了故障，其依赖服务也会发生故障，此时就会发生故障的蔓延，最终导致系统瘫痪。Hystrix实现了断路器模式，当某个服务发生故障时，通过断路器的监控，给调用方返回一个错误响应，而不是长时间的等待，这样就不会使得调用方由于长时间得不到响应而占用线程，从而防止故障的蔓延。Hystrix具备服务降级、服务熔断、线程隔离、请求缓存、请求合并及服务监控等强大功能。

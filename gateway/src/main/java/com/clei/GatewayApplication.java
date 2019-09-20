package com.clei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        ZonedDateTime beforeTime = LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault());
        ZonedDateTime afterTime = LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault());
        return builder.routes()
                // 前面的http://是必要的，去掉会报错
                // path 断言
                .route(r ->r.path("/jd").uri("https://www.jd.com").id("jd_route"))
                .route(r ->r.path("/bd").uri("https://www.baidu.com").id("bd_route"))
                // 时间 断言
                .route(r ->r.before(beforeTime).uri("https://www.baidu.com").id("before_route"))
                .route(r ->r.after(afterTime).uri("https://www.jd.com").id("after_route"))
                .route(r ->r.between(beforeTime,afterTime).uri("https://www.qidian.com").id("between_route"))
                // cookie 断言 使用order是为了先于前面的时间断言
                .route(r -> r.cookie("clei","kiya").uri("https://www.aliyun.com").id("cookie_route").order(-9999))
                // header 断言
                .route(r -> r.header("token").uri("https://www.aliyun.com").id("cookie_route").order(-9999))
                .route(r -> r.header("clei","kiya").uri("https://www.aliyun.com").id("cookie_route").order(-9999))
                .build();
    }
}

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
        String uri = "http://127.0.0.1:1010/common";
        return builder.routes()
                // 前面的http://是必要的，去掉会报错
                // path 断言
                .route(r -> r.path("/path1").uri(uri + "/path1").id("path_route1"))
                .route(r -> r.path("/path2").uri(uri + "/path2").id("path_route2"))
                // 时间 断言
                .route(r -> r.before(beforeTime).uri(uri + "/before").id("/before_route").order(1))
                .route(r -> r.after(afterTime).uri(uri + "/after").id("/after_route").order(1))
                .route(r -> r.between(beforeTime,afterTime).uri(uri + "/between").id("between_route").order(1))
                // cookie 断言 使用order是为了先于前面的时间断言
                .route(r -> r.cookie("clei","kiya").uri(uri + "/cookie").id("cookie_route"))
                // header 断言
                .route(r -> r.header("token").uri(uri + "/header1").id("header1_route1"))
                .route(r -> r.header("clei","kiya").uri(uri + "/header2").id("header_route2"))
                // host 断言 从哪里发来的要把端口号加上(若是80可以省略)
                .route(r -> r.host("*localhost:1000").uri(uri + "/host").id("host_route").order(15))
                // method 断言
                .route(r -> r.method("GET").uri(uri + "/get").id("method_route1").order(10))
                .route(r -> r.method("POST").uri(uri + "/post").id("method_route2").order(10))
                // query 断言
                .route(r -> r.query("key1").uri(uri + "/query1").id("query_route1"))
                .route(r -> r.query("key2","value").uri(uri + "/query2").id("query_route2"))
                // remoteAddr 断言
                .route(r -> r.remoteAddr("127.0.0.1","localhost","192.168.1.15").uri(uri + "/remoteAddr").id("remoteAddr_route").order(5))
                // Spring Cloud Gateway 的 内置 Filter
                // AddRequestHeader 过滤器工厂
                .route(r -> r.path("/addParam").filters(f -> f.addRequestParameter("sign","clei")).uri(uri + "/addParam").id("addRequestParam_route"))
                .route(r -> r.path("/addHeader").filters(f -> f.addRequestHeader("sign","clei")).uri(uri + "/addHeader").id("addRequestHeader_route"))
                .build();
    }
}

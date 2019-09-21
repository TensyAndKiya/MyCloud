package com.clei.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ReceiverController {

    @RequestMapping("common/{path}")
    public String path1(@PathVariable("path") String path,HttpServletRequest request){
        if(path.contains("addHeader")){
            System.out.println("sign : " + request.getHeader("sign"));
        }else if(path.contains("addParam")){
            System.out.println("sign : " + request.getParameter("sign"));
        }else if(path.contains("header")){
            System.out.println("token: " + request.getHeader("token"));
            System.out.println("clei: " + request.getHeader("clei"));
        }else if(path.contains("cookie")){
            for(Cookie cookie : request.getCookies()){
                System.out.println(cookie.getName() + "  " +cookie.getValue());
            }
        }else if(path.contains("query")){
            System.out.println("key1: " + request.getParameter("key1"));
            System.out.println("key2: " + request.getParameter("key2"));
        }
        return path;
    }
}

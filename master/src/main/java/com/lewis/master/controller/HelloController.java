package com.lewis.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/11/3.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/say")
    public String sayHello(String name){
        System.out.println("got here");
        return "Hello";
    }
}

package com.lewis.master.controller;

import com.lewis.master.common.anno.Json;
import com.lewis.master.common.utils.JsonUtil;
import com.lewis.master.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/student")
    @ResponseBody
    public String testJson(@Json Student student){

        return JsonUtil.toString(student);
    }
}

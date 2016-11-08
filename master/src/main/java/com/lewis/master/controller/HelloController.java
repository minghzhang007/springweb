package com.lewis.master.controller;

import com.lewis.master.common.anno.Json;
import com.lewis.master.common.anno.ResponseJson;
import com.lewis.master.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

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
    @ResponseJson
    public Student testJson(@Json Student student){
        List<String> hobbies = new LinkedList<String>();
        hobbies.add("singing");
        hobbies.add("dancing");
        hobbies.add("reading");
        return student;
    }
}

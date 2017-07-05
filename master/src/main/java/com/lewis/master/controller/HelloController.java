package com.lewis.master.controller;

import com.lewis.master.common.anno.CacheAnno;
import com.lewis.master.common.anno.Json;
import com.lewis.master.common.anno.ResponseJson;
import com.lewis.master.common.cache.CacheUtil;
import com.lewis.master.domain.Student;
import com.lewis.master.service.IHelloService;
import com.lewis.master.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/11/3.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private IHelloService helloService;

    @Resource
    private TestService testService;

    @RequestMapping("/say")
    public String sayHello(String name){
        System.out.println("got here");
        return "Hello";
    }

    @RequestMapping("/student")
    @ResponseJson
    //@CacheAnno(keyPrefix = "student",exprie = 600)
    public Student testJson(@Json Student student){
        Student wo = helloService.getStudent(500);

        testService.sayHello();
        return wo;
    }


    @RequestMapping("/getStudent")
    @ResponseJson
    //@CacheAnno(keyPrefix = "getStudent",exprie = 600)
    public Student testJson(){
        Student wo = helloService.getStudent(500);
        return wo;
    }

    @RequestMapping("/get")
    @ResponseJson
    public Student getStudent(){
        Student wo = cacheUtil.hgetAll("wo", Student.class);
        if (wo == null) {
            wo = helloService.getStudent(100);
            cacheUtil.setHashCache("wo",wo);
        }
        return wo;
    }

    @RequestMapping("/set")
    public void updateStudent(){
        cacheUtil.setHashFieldCache("wo","name","张明华");
    }
}

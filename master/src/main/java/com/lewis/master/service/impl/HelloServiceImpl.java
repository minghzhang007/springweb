package com.lewis.master.service.impl;

import com.lewis.master.domain.Student;
import com.lewis.master.service.IHelloService;
import com.lewis.master.service.MyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class HelloServiceImpl implements IHelloService {

    @Resource
    private MyService myService;

    public Student getStudent(int id) {
        Student student = new Student();
        student.setId(id);
        student.setName("lewis");
        student.setBirthday("2016-12-12");
        student.setGender(1);
        List<String> hobbys = new ArrayList<String>();
        hobbys.add("singing");
        hobbys.add("dancing");
        hobbys.add("reading");
        student.setHobbys(hobbys);
        myService.test();
        return student;
    }

    public MyService getMyService() {
        return myService;
    }

    public void setMyService(MyService myService) {
        this.myService = myService;
    }
}

package com.lewis.master.service.impl;

import com.lewis.master.common.anno.LogAnno;
import com.lewis.master.domain.Student;
import com.lewis.master.service.IHelloService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class HelloServiceImpl implements IHelloService {

    @LogAnno(doLogReqParamGreaterCostTime = 1000)
    public Student getStudent(int id) {
        Student student = new Student();
        student.setId(id);
        student.setName("lewis");
        student.setBirthday("2016-12-12");
        student.setGender(1);
        student.setHobbys(Arrays.asList(new String[]{"singing","dancing","reading"}));
        try {
            Thread.sleep(1005);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return student;
    }
}

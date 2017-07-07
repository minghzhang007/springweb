package com.lewis.master.service.impl;

import com.lewis.master.common.anno.Replaceable;
import com.lewis.master.common.concurrent.currentlimit.CurrentLimitEnum;
import com.lewis.master.common.concurrent.currentlimit.factory.DefaultCurrentLimitStrategyFactoryImpl;
import com.lewis.master.common.concurrent.currentlimit.strategy.CurrentLimitStrategy;
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

    @Replaceable(name = "currentLimitStrategy")
    private CurrentLimitStrategy currentLimitStrategy = DefaultCurrentLimitStrategyFactoryImpl.getInstance().getCurrentLimitStrategy(CurrentLimitEnum.CONCURRENT_LIMIT);

    @Resource
    private MyService            myService;

    public Student getStudent(int id) {
        Student student = new Student();
        if(currentLimitStrategy.acquire()){
            try{
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
            }finally {
                currentLimitStrategy.release();
            }
        }
        return student;
    }

    public MyService getMyService() {
        return myService;
    }

    public void setMyService(MyService myService) {
        this.myService = myService;
    }

    public CurrentLimitStrategy getCurrentLimitStrategy() {
        return currentLimitStrategy;
    }

    public void setCurrentLimitStrategy(CurrentLimitStrategy currentLimitStrategy) {
        this.currentLimitStrategy = currentLimitStrategy;
    }
}

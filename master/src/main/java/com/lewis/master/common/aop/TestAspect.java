package com.lewis.master.common.aop;

import org.aspectj.lang.JoinPoint;

/**
 * Created by zhangminghua on 2017/2/20.
 */
public class TestAspect {

    public void doAfter(){
        System.out.println("doAfter....");
    }

    public void doBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("doBefore....");
    }
}

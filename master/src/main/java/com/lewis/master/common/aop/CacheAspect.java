package com.lewis.master.common.aop;

import com.lewis.master.common.anno.CacheAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by zhangminghua on 2016/11/10.
 */
@Component
@Aspect
public class CacheAspect {

    @Around(value = "@annotation(cacheAnno)",argNames = "joinPoint,cacheAnno")
    public Object  getCache(ProceedingJoinPoint joinPoint, CacheAnno cacheAnno){


        return null;
    }
}

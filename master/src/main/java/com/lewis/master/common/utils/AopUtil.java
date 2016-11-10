package com.lewis.master.common.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Administrator on 2016/11/10.
 */
public final class AopUtil {

    private AopUtil(){}

    public static Class getReturnTypeOfJoinPoint(ProceedingJoinPoint joinPoint) {
        Class returnType = null;
        Signature signature = joinPoint.getSignature();
        if (signature != null && signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature)signature;
            returnType = methodSignature.getReturnType();
        }
        return returnType;
    }
}

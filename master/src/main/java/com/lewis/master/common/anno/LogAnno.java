package com.lewis.master.common.anno;

import com.lewis.master.common.vo.ResponseVo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangminghua on 2016/11/9.
 * log record
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnno {

     Class<?> returnType() default ResponseVo.class;

    /**
     * whether to record the time spent
     */
    boolean recordTime() default true;

    /**
     *when the cost time of this method-execution, greater the returned value,
     * do log request param of this method,unit milliseconds
     */
    int doLogReqParamGreaterCostTime() default 2000;

}

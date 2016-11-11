package com.lewis.master.common.aop;

import com.lewis.master.common.anno.CacheAnno;
import com.lewis.master.common.anno.IgnoreCacheField;
import com.lewis.master.common.cache.CacheUtil;
import com.lewis.master.common.utils.AopUtil;
import com.lewis.master.common.utils.ArrayUtil;
import com.lewis.master.common.utils.beanUitl.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * Created by zhangminghua on 2016/11/10.
 */
@Component
@Aspect
public class CacheAspect {

    private Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Resource
    private CacheUtil cacheUtil;

    @Around(value = "@annotation(cacheAnno)", argNames = "joinPoint,cacheAnno")
    public Object getCache(ProceedingJoinPoint joinPoint, CacheAnno cacheAnno) {
        //1.获取cacheKey,获取returnType
        Class returnType = AopUtil.getReturnTypeOfJoinPoint(joinPoint);
        Object[] args = joinPoint.getArgs();
        //待增强方法有参数值
        String cacheKey = null;
        if (ArrayUtil.isNotEmpty(args)) {
            cacheKey = getCacheKey(cacheAnno, args[0]);
        }
        //待增强方法无参数值
        else {
            cacheKey = getCacheKey(cacheAnno, null);
        }
        //2.根据cacheKey,returnType 从缓存中获取cacheValue
        Object returnObj = cacheUtil.hgetAll(cacheKey, returnType);

        //3.若cacheValue有值，则返回；若cachevalue为null,则调用目标方法，并将返回结果进行缓存
        if (returnObj != null) {
            return returnObj;
        }else{
            try {
                returnObj = joinPoint.proceed();
                if (returnObj != null) {
                    cacheUtil.setHashCache(cacheKey,returnObj);
                }
            } catch (Throwable throwable) {
                logger.error("getCache occur {},cacheAnno is {}",throwable.getCause(),cacheAnno.keyPrefix());
            }
        }
        return returnObj;
    }

    private String getCacheKey(CacheAnno cacheAnno, Object param) {
        StringBuilder sb = new StringBuilder(cacheAnno.keyPrefix());
        if (param != null) {
            if (param instanceof String || param instanceof Integer) {
                sb.append("_").append(param);
            }else{
                Field[] fields = BeanUtil.getFields(param.getClass(), true);
                if (ArrayUtil.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        if (field.getAnnotation(IgnoreCacheField.class) == null) {
                            sb.append("_").append(BeanUtil.getFieldValue(param, field));
                        }
                    }
                }
            }
        }
        return sb.toString();
    }


}

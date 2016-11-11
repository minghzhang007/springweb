package com.lewis.master.common.aop;


import com.lewis.master.common.anno.LogAnno;
import com.lewis.master.common.utils.AopUtil;
import com.lewis.master.common.utils.ArrayUtil;
import com.lewis.master.common.utils.JsonUtil;
import com.lewis.master.common.vo.ResponseVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhangminghua on 2016/11/9.
 */
@Component
@Aspect
public class LogAspect {

    private Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Around(value = "@annotation(logAnno)", argNames = "joinPoint,logAnno")
    public Object doLog(ProceedingJoinPoint joinPoint, LogAnno logAnno) {
        Signature signature = joinPoint.getSignature();
        Class returnType = AopUtil.getReturnTypeOfJoinPoint(joinPoint);
        boolean alreadyDoLogRequestParam = false;
        StringBuilder sb = new StringBuilder();
        Object returnResult = null;
        try {
            String className = signature.getDeclaringTypeName().substring(signature.getDeclaringTypeName().lastIndexOf(".") + 1);
            sb.append(className)
                    .append(".")
                    .append(signature.getName());
            long beginTime = System.currentTimeMillis();
            returnResult = joinPoint.proceed();
            long costTime = System.currentTimeMillis() - beginTime;
            if (costTime > logAnno.doLogReqParamGreaterCostTime()) {
                if (ArrayUtil.isNotEmpty(joinPoint.getArgs())) {
                    sb.append(", param is ").append(JsonUtil.toString(joinPoint.getArgs()[0]));
                    alreadyDoLogRequestParam = true;
                }
            }
            if (logAnno.recordTime()) {
                sb.append(" costTime#").append(costTime);
            }
            if (returnType != null) {
                //返回值为ResponseVo类型
                if (ResponseVo.class.isAssignableFrom(returnType)) {
                    if (returnResult != null) {
                        ResponseVo resultVo = (ResponseVo) returnResult;
                        if (resultVo.getData() == null) {
                            doLogWhenReturnResultIsNull(joinPoint, alreadyDoLogRequestParam, sb);
                        }
                    } else {
                        doLogWhenReturnResultIsNull(joinPoint, alreadyDoLogRequestParam, sb);
                    }
                }
                //返回值为自定义类型
                else if(returnType != void.class){
                    if (returnResult == null) {
                        doLogWhenReturnResultIsNull(joinPoint, alreadyDoLogRequestParam, sb);
                    }
                }
            }

        } catch (Throwable throwable) {
            sb.append(",occur error ")
                    .append(throwable.toString())
                    .append(",");
            if (ArrayUtil.isNotEmpty(throwable.getStackTrace())) {
                sb.append(throwable.getStackTrace()[0]);
            }
            if (ArrayUtil.isNotEmpty(joinPoint.getArgs())) {
                sb.append(", param is ").append(JsonUtil.toString(joinPoint.getArgs()[0]));
            }
        }
        LOG.error(sb.toString());
        System.out.println(sb.toString());
        return returnResult;
    }

    private void doLogWhenReturnResultIsNull(ProceedingJoinPoint joinPoint, boolean alreadyDoLogRequestParam, StringBuilder sb) {
        sb.append(",return result is null");
        if (ArrayUtil.isNotEmpty(joinPoint.getArgs()) && !alreadyDoLogRequestParam) {
            sb.append(",param is ").append(JsonUtil.toString(joinPoint.getArgs()[0]));
        }
    }
}

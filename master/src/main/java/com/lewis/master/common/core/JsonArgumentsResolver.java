package com.lewis.master.common.core;

import com.lewis.master.common.anno.Json;
import com.lewis.master.common.utils.JsonUtil;
import com.lewis.master.common.utils.StringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by zhangminghua on 2016/11/7.
 */
public class JsonArgumentsResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Json.class);
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Json jsonAnno = methodParameter.getParameterAnnotation(Json.class);
        Class<?> parameterType = methodParameter.getParameterType();
        String requestParam = getAllRequestParams(nativeWebRequest);
        if (parameterType != null && StringUtil.isNotEmpty(requestParam)) {
            Object o = JsonUtil.toBean(requestParam, parameterType);
            return o;
        }
        System.out.println(requestParam);
        return null;
    }

    private String getAllRequestParams(NativeWebRequest nativeWebRequest) throws IOException {
        String requestParam = null;
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String method = httpServletRequest.getMethod();
        if ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            requestParam =  httpServletRequest.getQueryString();
            requestParam = requestParam.replaceAll("%22","\"");
        }else{
            StringBuilder buffer = new StringBuilder();
            String line;
            BufferedReader reader = httpServletRequest.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            requestParam = buffer.toString();
        }
        return requestParam;
    }

}

package com.lewis.master.common.core;

import com.lewis.master.common.utils.ListUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * RequestMappingHandlerAdapter重置处理类
 * Created by zhangminghua on 2016/11/7.
 */
public class RequestMappingHandlerPostProcessor implements BeanPostProcessor {

    private List<Class<HandlerMethodArgumentResolver>> removedArgumentResolvers = new ArrayList<Class<HandlerMethodArgumentResolver>>();

    private List<Class<HandlerMethodReturnValueHandler>> removedReturnValueHandlers = new ArrayList<Class<HandlerMethodReturnValueHandler>>();

    private List<Class<HandlerMethodReturnValueHandler>> addedReturnValueHandlers = new ArrayList<Class<HandlerMethodReturnValueHandler>>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter) {
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
            removeArgumentResolvers(adapter.getArgumentResolvers(), adapter);
            removeReturnValueHandlers(adapter.getReturnValueHandlers(), adapter);
            addReturnValueHandlers(adapter.getReturnValueHandlers(),adapter);
        }
        return bean;
    }

    /**
     * 添加指定的HandlerMethodReturnValueHandler
     * @param returnValueHandlers
     * @param adapter
     */
    private void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers, RequestMappingHandlerAdapter adapter) {
        if (ListUtil.isNotEmpty(addedReturnValueHandlers)) {
            List<HandlerMethodReturnValueHandler> totalReturnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>(16);
            totalReturnValueHandlers.addAll(returnValueHandlers);
            try {
                for (Class<HandlerMethodReturnValueHandler> addedReturnValueHandler : addedReturnValueHandlers) {
                    if (!containsElement(totalReturnValueHandlers, addedReturnValueHandler)) {
                        totalReturnValueHandlers.add(addedReturnValueHandler.newInstance());
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            adapter.setReturnValueHandlers(totalReturnValueHandlers);
        }
    }

    private boolean containsElement(List<HandlerMethodReturnValueHandler> returnValueHandlers, Class<HandlerMethodReturnValueHandler> addedReturnValueHandler) {
        if (ListUtil.isNotEmpty(returnValueHandlers) && addedReturnValueHandler != null) {
            for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
                if (returnValueHandler.getClass().equals(addedReturnValueHandler)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 移除指定的系统默认加载ArgumentResolver
     *
     * @param argumentResolvers
     */
    private void removeArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers,
                                         RequestMappingHandlerAdapter adapter) {
        if (removedArgumentResolvers == null || removedArgumentResolvers.size() == 0)
            return;
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
            if (removedArgumentResolvers.contains(argumentResolver.getClass())) {
                continue;
            }
            resolvers.add(argumentResolver);
        }
        adapter.setArgumentResolvers(resolvers);
    }

    /**
     * 移除指定的系统默认加载的MehtodReturnValueHandler
     *
     * @param returnValueHandlers
     */
    private void removeReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers,
                                           RequestMappingHandlerAdapter adapter) {
        if (removedReturnValueHandlers == null || removedReturnValueHandlers.size() == 0)
            return;
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>();
        for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
            if (removedReturnValueHandlers.contains(returnValueHandler.getClass())) {
                continue;
            }
            handlers.add(returnValueHandler);
        }
        adapter.setReturnValueHandlers(handlers);
    }


    public List<Class<HandlerMethodArgumentResolver>> getRemovedArgumentResolvers() {
        return removedArgumentResolvers;
    }

    public void setRemovedArgumentResolvers(List<Class<HandlerMethodArgumentResolver>> removedArgumentResolvers) {
        this.removedArgumentResolvers = removedArgumentResolvers;
    }

    public List<Class<HandlerMethodReturnValueHandler>> getRemovedReturnValueHandlers() {
        return removedReturnValueHandlers;
    }

    public void setRemovedReturnValueHandlers(List<Class<HandlerMethodReturnValueHandler>> removedReturnValueHandlers) {
        this.removedReturnValueHandlers = removedReturnValueHandlers;
    }

    public List<Class<HandlerMethodReturnValueHandler>> getAddedReturnValueHandlers() {
        return addedReturnValueHandlers;
    }

    public void setAddedReturnValueHandlers(List<Class<HandlerMethodReturnValueHandler>> addedReturnValueHandlers) {
        this.addedReturnValueHandlers = addedReturnValueHandlers;
    }
}

package com.lewis.master.common.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/9.
 */
@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public synchronized static ApplicationContext getContext() {
        return context;
    }

}

package com.lewis.master.common.core.dynamicregistry;

import com.lewis.master.common.core.AppContext;
import com.lewis.master.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Bean动态注册到spring容器中
 * @author zmh46712
 * @version Id: DynamicBeanRegistry, v 0.1 2017/7/5 11:33 zmh46712 Exp $
 */
public abstract class AbstractDynamicBeanRegistry {

    public void dynamicRegistry(Class registryType){
        String beanName = registryType.getSimpleName().substring(0,1).toLowerCase()+registryType.getSimpleName().substring(1);
        ApplicationContext appContext = AppContext.getContext();
        if(appContext instanceof ConfigurableApplicationContext){
            ConfigurableApplicationContext cac = (ConfigurableApplicationContext) appContext;
            ConfigurableListableBeanFactory beanFactory = cac.getBeanFactory();
            if(beanFactory instanceof DefaultListableBeanFactory){
                DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory)beanFactory;
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(registryType);
                addPropertyReference(beanDefinitionBuilder);
                listableBeanFactory.setAllowBeanDefinitionOverriding(true);
                listableBeanFactory.registerBeanDefinition(beanName,beanDefinitionBuilder.getRawBeanDefinition());
            }
        }

    }

    protected abstract void addPropertyReference(BeanDefinitionBuilder beanDefinitionBuilder);
}

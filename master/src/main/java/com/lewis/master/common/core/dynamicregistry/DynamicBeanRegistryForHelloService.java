package com.lewis.master.common.core.dynamicregistry;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.stereotype.Service;

/**
 * @author zmh46712
 * @version Id: DynamicBeanRegistryForHelloService, v 0.1 2017/7/5 11:40 zmh46712 Exp $
 */
@Service
public class DynamicBeanRegistryForHelloService extends AbstractDynamicBeanRegistry {

    protected void addPropertyReference(BeanDefinitionBuilder beanDefinitionBuilder) {
        beanDefinitionBuilder.addPropertyReference("myService", "myServiceImpl");
    }
}

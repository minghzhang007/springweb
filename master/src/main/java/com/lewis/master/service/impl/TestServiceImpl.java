package com.lewis.master.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lewis.master.common.core.dynamicregistry.DynamicBeanRegistryForHelloService;
import com.lewis.master.service.TestService;

/**
 * @author zmh46712
 * @version Id: TestServiceImpl, v 0.1 2017/7/5 10:35 zmh46712 Exp $
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private DynamicBeanRegistryForHelloService dynamicBeanRegistryForHelloService;

    public void sayHello() {
        dynamicBeanRegistryForHelloService.dynamicRegistry(HelloServiceImpl.class);
    }
}

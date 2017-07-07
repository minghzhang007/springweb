package com.lewis.master.common.core.dynamicreplace;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Service;

import com.lewis.master.common.concurrent.currentlimit.strategy.CurrentLimitStrategy;

/**
 * @author zmh46712
 * @version Id: CurrentLimitReplaceService, v 0.1 2017/7/7 11:31 zmh46712 Exp $
 */
@Service
public class CurrentLimitReplaceService extends AbstractDynamicReplaceComponent {

    private CurrentLimitStrategy newCurrentLimitStrategy;

    @Override
    protected <T> void doReplaceField(Method writeMethod, T bean) {
        try {
            writeMethod.invoke(bean, newCurrentLimitStrategy);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setNewCurrentLimitStrategy(CurrentLimitStrategy newCurrentLimitStrategy) {
        this.newCurrentLimitStrategy = newCurrentLimitStrategy;
    }
}

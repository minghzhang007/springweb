package com.lewis.master.common.core.dynamicreplace;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;

import com.lewis.master.common.anno.Replaceable;
import com.lewis.master.common.core.AppContext;

/**
 * @author zmh46712
 * @version Id: DynamicReplaceComponent, v 0.1 2017/7/7 11:02 zmh46712 Exp $
 */
public abstract class AbstractDynamicReplaceComponent {

    public <T> void replace(Class<T> type, String replaceableName) {
        ApplicationContext context = AppContext.getContext();
        T bean = context.getBean(type);
        if (bean != null) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            if (ArrayUtils.isEmpty(declaredFields)) {
                return;
            }
            for (Field field : declaredFields) {
                if (isReplaceAble(field, replaceableName)) {
                    try {
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), bean.getClass());
                        Method writeMethod = pd.getWriteMethod();
                        doReplaceField(writeMethod, bean);
                        break;
                    } catch (IntrospectionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected abstract <T> void doReplaceField(Method writeMethod, T bean);

    private boolean isReplaceAble(Field field, String replaceableName) {
        Replaceable replaceAble = field.getAnnotation(Replaceable.class);
        return replaceAble != null && replaceAble.name().equals(replaceableName);
    }
}

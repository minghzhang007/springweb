package com.lewis.master.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zmh46712
 * @version Id: ReplaceAble, v 0.1 2017/7/7 10:55 zmh46712 Exp $
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Replaceable {

    String name() default "name";
}

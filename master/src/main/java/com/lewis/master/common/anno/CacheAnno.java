package com.lewis.master.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangminghua on 2016/11/10.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheAnno {

    String keyPrefix() default "KEY_PREFIX";

    int exprie() default 1800;
}

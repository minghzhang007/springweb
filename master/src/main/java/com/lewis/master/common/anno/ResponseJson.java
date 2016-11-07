package com.lewis.master.common.anno;

import java.lang.annotation.*;

/**
 * Created by zhangminghua on 2016/11/7.
 * 方法返回值处理注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseJson {
}

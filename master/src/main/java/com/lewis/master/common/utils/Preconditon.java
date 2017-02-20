package com.lewis.master.common.utils;

/**
 * Created by zhangminghua on 2016/11/10.
 */
public interface Preconditon<T> {

    boolean evaluate(T t);

}

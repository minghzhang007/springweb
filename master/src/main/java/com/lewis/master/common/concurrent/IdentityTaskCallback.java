package com.lewis.master.common.concurrent;

/**
 * Created by Administrator on 2016/11/10.
 * 标识性任务的回调接口
 */
public interface IdentityTaskCallback<T> extends TaskCallback<T>{
    String identity();
}

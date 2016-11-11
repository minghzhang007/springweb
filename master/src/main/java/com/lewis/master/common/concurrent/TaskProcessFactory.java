package com.lewis.master.common.concurrent;

/**
 * Created by zhangminghua on 2016/11/11.
 */
public interface TaskProcessFactory {

    TaskProcessor createTaskProcessor(String domain);
}

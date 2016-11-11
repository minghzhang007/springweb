package com.lewis.master.common.concurrent;

/**
 * Created by zhangminghua on 2016/11/11.
 */
public final class Executor {

    private Executor(){}

    public static TaskProcessor getCommonTaskProcessor(){
        return TaskProcessManager.getTaskProcessor();
    }
}

package com.lewis.master.common.concurrent.currentlimit.strategy;

/**
 * 限流策略接口
 * @author zmh46712
 * @version Id: CurrentLimitStrategy, v 0.1 2017/6/19 14:57 zmh46712 Exp $
 */
public interface CurrentLimitStrategy {

    /**
     * 获取一个许可
     * @return
     */
    boolean acquire();

    /**
     * 获取指定个数的许可
     * @param permits
     * @return
     */
    boolean acquire(int permits);

    /**
     * 释放指定个数的许可
     * @return
     */
    default boolean release(int permits) {
        return true;
    }

    /**
     * 释放一个许可
     * @return
     */
    default boolean release() {
        return true;
    }

}

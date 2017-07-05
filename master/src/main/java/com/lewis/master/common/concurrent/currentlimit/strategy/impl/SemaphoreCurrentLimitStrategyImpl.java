package com.lewis.master.common.concurrent.currentlimit.strategy.impl;

import java.util.concurrent.Semaphore;

import com.lewis.master.common.concurrent.currentlimit.strategy.CurrentLimitStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 限制并发访问数据的限流实现
 * @author zmh46712
 * @version Id: SemaphoreCurrentLimitStrategyImpl, v 0.1 2017/6/19 15:00 zmh46712 Exp $
 */
public class SemaphoreCurrentLimitStrategyImpl implements CurrentLimitStrategy {

    private Logger logger = LoggerFactory.getLogger(SemaphoreCurrentLimitStrategyImpl.class);

    /**
     * 并发信号量
     */
    private Semaphore semaphore;

    public SemaphoreCurrentLimitStrategyImpl(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    @Override
    public boolean acquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean acquire(int permits) {
        try {
            semaphore.acquire(permits);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean release(int permits) {
        semaphore.release(permits);
        return true;
    }

    @Override
    public boolean release() {
        semaphore.release();
        return true;
    }
}

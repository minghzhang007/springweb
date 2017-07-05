package com.lewis.master.common.concurrent.currentlimit.strategy.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.lewis.master.common.concurrent.currentlimit.strategy.CurrentLimitStrategy;

/**
 * 令牌桶算法的限流实现 每秒允许你多少次访问
 * @author zmh46712
 * @version Id: RateLimiterCurrentLimitStrategyImpl, v 0.1 2017/6/19 15:07 zmh46712 Exp $
 */
public class RateLimiterCurrentLimitStrategyImpl implements CurrentLimitStrategy {

    /**
     * 令牌桶算法的限流实现
     */
    private RateLimiter rateLimiter;

    public RateLimiterCurrentLimitStrategyImpl(double permitsPerSecond) {
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }

    @Override
    public boolean acquire() {
        rateLimiter.acquire();
        return true;
    }

    @Override
    public boolean acquire(int permits) {
        rateLimiter.acquire(permits);
        return true;
    }

}

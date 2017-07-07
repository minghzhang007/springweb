package com.lewis.master.common.concurrent.currentlimit.factory;

import com.lewis.master.common.concurrent.currentlimit.CurrentLimitEnum;
import com.lewis.master.common.concurrent.currentlimit.strategy.CurrentLimitStrategy;
import com.lewis.master.common.concurrent.currentlimit.strategy.impl.RateLimiterCurrentLimitStrategyImpl;
import com.lewis.master.common.concurrent.currentlimit.strategy.impl.SemaphoreCurrentLimitStrategyImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * @author zmh46712
 * @version Id: DefaultCurrentLimitStrategyFactoryImpl, v 0.1 2017/6/19 15:21 zmh46712 Exp $
 */
public class DefaultCurrentLimitStrategyFactoryImpl implements CurrentLimitStrategyFactory {

    private static DefaultCurrentLimitStrategyFactoryImpl instance = new DefaultCurrentLimitStrategyFactoryImpl();

    /**
     * 限流策略容器: 每个限流策略枚举，只有一个限流策略实例
     */
    private static Map<CurrentLimitEnum, CurrentLimitStrategy> currentLimitStrategyContainer = new ConcurrentHashMap<>(16);

    @Override
    public CurrentLimitStrategy getCurrentLimitStrategy(CurrentLimitEnum currentLimitEnum) {
        CurrentLimitStrategy currentLimitStrategy = currentLimitStrategyContainer.get(currentLimitEnum);
        if (currentLimitStrategy == null) {
            synchronized (DefaultCurrentLimitStrategyFactoryImpl.class) {
                currentLimitStrategy = currentLimitStrategyContainer.get(currentLimitEnum);
                if (currentLimitStrategy == null) {
                    currentLimitStrategy = createCurrentLimitStrategy(currentLimitEnum);
                    currentLimitStrategyContainer.put(currentLimitEnum, currentLimitStrategy);
                }
            }
        }
        return currentLimitStrategy;
    }

    /**
     * 创建指定限流策略
     * @param currentLimitEnum 限流策略枚举
     * @return 限流策略
     */
    private CurrentLimitStrategy createCurrentLimitStrategy(CurrentLimitEnum currentLimitEnum) {
        switch (currentLimitEnum) {
            case CONCURRENT_LIMIT:
                return new SemaphoreCurrentLimitStrategyImpl(32);
            case RATE_LIMIT:
                return new RateLimiterCurrentLimitStrategyImpl(10);
            default:
                return new SemaphoreCurrentLimitStrategyImpl(32);
        }
    }

    public static DefaultCurrentLimitStrategyFactoryImpl getInstance() {
        return instance;
    }
}

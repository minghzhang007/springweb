package com.lewis.master.common.concurrent.currentlimit.factory;


import com.lewis.master.common.concurrent.currentlimit.CurrentLimitEnum;
import com.lewis.master.common.concurrent.currentlimit.strategy.CurrentLimitStrategy;

/**
 * 限流策略工厂
 * @author zmh46712
 * @version Id: CurrentLimitStrategyFactory, v 0.1 2017/6/19 15:17 zmh46712 Exp $
 */
public interface CurrentLimitStrategyFactory {

    /**
     * 根据指定的限流策略枚举获取对应的限流策略实例
     * @param currentLimitEnum 限流策略枚举
     * @return 限流策略实例
     */
    CurrentLimitStrategy getCurrentLimitStrategy(CurrentLimitEnum currentLimitEnum);

}

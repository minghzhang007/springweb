package com.lewis.master.common.concurrent.currentlimit;

/**
 * 限流策略枚举
 * @author zmh46712
 * @version Id: CurrrentLimitEnums, v 0.1 2017/6/19 15:18 zmh46712 Exp $
 */
public enum CurrentLimitEnum {

                                /**
                                 * 并发数量限流
                                 */
                                CONCURRENT_LIMIT,
                                /**
                                 * 速率限流
                                 */
                                RATE_LIMIT
}

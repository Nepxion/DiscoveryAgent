package com.nepxion.discovery.agent.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.agent.threadlocal.AbstractThreadLocalHook;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;

public class StrategyTracerContextHook extends AbstractThreadLocalHook {
    @Override
    public Object create() {
        return StrategyTracerContext.getCurrentContext().getSpan();
    }

    @Override
    public void before(Object object) {
        StrategyTracerContext.getCurrentContext().setSpan(object);
    }

    @Override
    public void after() {
        StrategyTracerContext.clearCurrentContext();
    }
}
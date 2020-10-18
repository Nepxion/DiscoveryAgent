package com.nepxion.discovery.agent.plugin.strategy.service;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.agent.plugin.AbstractPlugin;

public class DiscoveryServicePlugin extends AbstractPlugin {
    private Boolean threadServiceEnabled = Boolean.valueOf(System.getProperty("thread.service.enabled", "true"));

    @Override
    protected String getMatcherClassName() {
        return "com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext";
    }

    @Override
    protected String getHookClassName() {
        return ServiceStrategyContextHook.class.getName();
    }

    @Override
    protected boolean isEnabled() {
        return threadServiceEnabled;
    }
}
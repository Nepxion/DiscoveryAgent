package com.nepxion.discovery.agent.plugin.strategy.zuul;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.agent.plugin.AbstractPlugin;

public class DiscoveryZuulPlugin extends AbstractPlugin {
    private Boolean threadZuulEnabled = Boolean.valueOf(System.getProperty("thread.zuul.enabled", "true"));

    @Override
    protected String getMatcherClassName() {
        return "com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext";
    }

    @Override
    protected String getHookClassName() {
        return ZuulStrategyContextHook.class.getName();
    }

    @Override
    protected boolean isEnabled() {
        return threadZuulEnabled;
    }
}
package com.nepxion.discovery.agent.plugin.strategy.gateway;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.agent.plugin.AbstractPlugin;

public class DiscoveryGatewayPlugin extends AbstractPlugin {
    @Override
    protected String getMatcherClassName() {
        return "com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext";
    }

    @Override
    protected String getHookClassName() {
        return GatewayStrategyContextHook.class.getName();
    }
}
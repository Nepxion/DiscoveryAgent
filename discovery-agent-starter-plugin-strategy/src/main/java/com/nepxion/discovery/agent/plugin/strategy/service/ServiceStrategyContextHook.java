package com.nepxion.discovery.agent.plugin.strategy.service;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.util.Map;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.nepxion.discovery.agent.plugin.thread.ThreadConstant;
import com.nepxion.discovery.agent.threadlocal.AbstractThreadLocalHook;
import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.decorator.ServiceStrategyRequestDecoratorFactory;

public class ServiceStrategyContextHook extends AbstractThreadLocalHook {
    private Boolean requestDecoratorEnabled = Boolean.valueOf(System.getProperty(ThreadConstant.THREAD_REQUEST_DECORATOR_ENABLED, "true"));

    @Override
    public Object create() {
        RequestAttributes request = RequestContextHolder.getRequestAttributes();
        if (requestDecoratorEnabled) {
            if (null != request) {
                request = ServiceStrategyRequestDecoratorFactory.decorateRequestAttributes(request);
            }
        }
        Map<String, Object> attributes = RpcStrategyContext.getCurrentContext().getAttributes();

        return new Object[] { request, attributes };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void before(Object object) {
        if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;

            if (objects[0] instanceof RequestAttributes) {
                RequestAttributes requestAttributes = (RequestAttributes) objects[0];
                RequestContextHolder.setRequestAttributes(requestAttributes);
                RestStrategyContext.getCurrentContext().setRequestAttributes(requestAttributes);
            }
            if (objects[1] instanceof Map) {
                RpcStrategyContext.getCurrentContext().setAttributes((Map<String, Object>) objects[1]);
            }
        }
    }

    @Override
    public void after() {
        RequestContextHolder.resetRequestAttributes();
        RestStrategyContext.clearCurrentContext();
        RpcStrategyContext.clearCurrentContext();
    }
}
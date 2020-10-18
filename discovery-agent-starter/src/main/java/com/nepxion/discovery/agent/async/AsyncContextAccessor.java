package com.nepxion.discovery.agent.async;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public interface AsyncContextAccessor {
    void setAsyncContext(AsyncContext asyncContext);

    AsyncContext getAsyncContext();
}
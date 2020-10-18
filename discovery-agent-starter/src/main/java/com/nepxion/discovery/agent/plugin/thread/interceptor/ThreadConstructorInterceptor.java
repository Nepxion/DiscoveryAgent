package com.nepxion.discovery.agent.plugin.thread.interceptor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.agent.async.AsyncContext;
import com.nepxion.discovery.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.agent.threadlocal.ThreadLocalCopier;

public class ThreadConstructorInterceptor {
    public static void before(Object object) {
        if (object instanceof AsyncContextAccessor) {
            AsyncContextAccessor asyncContextAccessor = (AsyncContextAccessor) object;
            Object[] objects = ThreadLocalCopier.create();
            asyncContextAccessor.setAsyncContext(new AsyncContext(objects));
        }
    }
}
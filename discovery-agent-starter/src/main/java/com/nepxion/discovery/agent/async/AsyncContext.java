package com.nepxion.discovery.agent.async;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public class AsyncContext {
    private Object[] objects;

    /**
     * Record the original thread and compare it with the thread that called runnable/callable.
     */
    private Thread originThread;

    public AsyncContext(Object[] objects) {
        this.objects = objects;
        this.originThread = Thread.currentThread();
    }

    public Object[] getObjects() {
        return objects;
    }

    public Thread getOriginThread() {
        return originThread;
    }
}
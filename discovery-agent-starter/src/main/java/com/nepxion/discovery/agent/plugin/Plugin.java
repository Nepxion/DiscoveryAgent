package com.nepxion.discovery.agent.plugin;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.agent.callback.TransformTemplate;

public abstract class Plugin {
    public abstract void install(TransformTemplate transformTemplate);

    protected boolean isAllowed() {
        return true;
    }

    protected boolean isEnabled() {
        return true;
    }
}
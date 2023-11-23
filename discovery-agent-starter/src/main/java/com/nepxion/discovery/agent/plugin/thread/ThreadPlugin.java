package com.nepxion.discovery.agent.plugin.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import com.nepxion.discovery.agent.callback.TransformTemplate;
import com.nepxion.discovery.agent.config.Config;
import com.nepxion.discovery.agent.logger.AgentLogger;
import com.nepxion.discovery.agent.matcher.MatcherFactory;
import com.nepxion.discovery.agent.matcher.MatcherOperator;
import com.nepxion.discovery.agent.plugin.Plugin;
import com.nepxion.discovery.agent.util.StringUtil;

public class ThreadPlugin extends Plugin {
    private static final AgentLogger LOG = AgentLogger.getLogger(ThreadPlugin.class.getName());

    @Override
    public void install(TransformTemplate transformTemplate) {
        Properties baseConfig = Config.INSTANCE.getConfig();
        String baseThreadScanPackages = baseConfig.getProperty(ThreadConstant.BASE_THREAD_SCAN_PACKAGES);
        if (StringUtil.isEmpty(baseThreadScanPackages)) {
            LOG.warn(String.format("Base Thread scan packages (%s) is null, ignore base thread context switch", ThreadConstant.BASE_THREAD_SCAN_PACKAGES));
        }

        String threadScanPackages = System.getProperty(ThreadConstant.THREAD_SCAN_PACKAGES);
        if (StringUtil.isEmpty(threadScanPackages)) {
            LOG.warn(String.format("Custom Thread scan packages (%s) is null, ignore custom thread context switch", ThreadConstant.THREAD_SCAN_PACKAGES));
        }

        LOG.info(String.format("Trace (%s) (%s) Runnable/Callable for thread context switch", baseThreadScanPackages, threadScanPackages));

        List<String> basePackages = StringUtil.tokenizeToStringList(baseThreadScanPackages, ThreadConstant.THREAD_SCAN_PACKAGES_DELIMITERS);
        List<String> customPackages = StringUtil.tokenizeToStringList(threadScanPackages, ThreadConstant.THREAD_SCAN_PACKAGES_DELIMITERS);
        HashSet<String> scanPackages = new HashSet<>();
        scanPackages.addAll(basePackages);
        scanPackages.addAll(customPackages);
        if (scanPackages.isEmpty()) {
            return;
        }

        RunnableTransformCallback runnableTransformCallback = new RunnableTransformCallback();
        CallableTransformCallback callableTransformCallback = new CallableTransformCallback();
        SupplierTransformCallback supplierTransformCallback = new SupplierTransformCallback();
        for (String basePackage : scanPackages) {
            MatcherOperator runnableInterfaceMatcherOperator = MatcherFactory.newPackageBasedMatcher(basePackage, ThreadConstant.RUNNABLE_CLASS_NAME);
            MatcherOperator callableInterfaceMatcherOperator = MatcherFactory.newPackageBasedMatcher(basePackage, ThreadConstant.CALLABLE_CLASS_NAME);
            MatcherOperator supplierInterfaceMatcherOperator = MatcherFactory.newPackageBasedMatcher(basePackage, ThreadConstant.SUPPLIER_CLASS_NAME);
            transformTemplate.transform(runnableInterfaceMatcherOperator, runnableTransformCallback);
            transformTemplate.transform(callableInterfaceMatcherOperator, callableTransformCallback);
            transformTemplate.transform(supplierInterfaceMatcherOperator, supplierTransformCallback);
        }

        LOG.info(String.format("%s install successfully", this.getClass().getSimpleName()));
    }

    public static class RunnableTransformCallback extends ThreadTransformCallback {
        @Override
        public String getImplMethodName() {
            return ThreadConstant.RUNNABLE_IMPL_METHOD_NAME;
        }
    }

    public static class CallableTransformCallback extends ThreadTransformCallback {

        @Override
        public String getImplMethodName() {
            return ThreadConstant.CALLABLE_IMPL_METHOD_NAME;
        }
    }

    public static class SupplierTransformCallback extends ThreadTransformCallback {

        @Override
        public String getImplMethodName() {
            return ThreadConstant.SUPPLIER_IMPL_METHOD_NAME;
        }
    }
}
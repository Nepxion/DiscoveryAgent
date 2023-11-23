package com.nepxion.discovery.agent.plugin.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import com.nepxion.discovery.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.agent.callback.TransformCallback;
import com.nepxion.discovery.agent.logger.AgentLogger;
import com.nepxion.discovery.agent.util.ClassInfo;

public abstract class ThreadTransformCallback implements TransformCallback {
    private static final AgentLogger LOG = AgentLogger.getLogger(ThreadTransformCallback.class.getName());

    @Override
    public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
            CtClass ctClass = classInfo.getCtClass();

            addField(ctClass, AsyncContextAccessor.class);

            CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
            for (CtConstructor ctConstructor : declaredConstructors) {
                ctConstructor.insertAfter(ThreadConstant.CONSTRUCTOR_INTERCEPTOR);
            }

            String implMethodName = getImplMethodName();
            CtMethod ctMethod = ctClass.getDeclaredMethod(implMethodName);
            if (null != ctMethod) {
                ctMethod.insertBefore(ThreadConstant.RUN_BEFORE_INTERCEPTOR);
                ctMethod.insertAfter(ThreadConstant.RUN_AFTER_INTERCEPTOR);
            }

            return ctClass.toBytecode();
        } catch (Exception e) {
            LOG.warn(String.format("Transform %s error, message:", className), e);

            return null;
        }
    }

    public void addField(CtClass ctClass, Class<?> clazz) {
        try {
            ClassPool aDefault = ClassPool.getDefault();
            CtClass makeInterface = aDefault.makeInterface(clazz.getName());
            ctClass.addInterface(makeInterface);

            Method[] methods = clazz.getDeclaredMethods();
            if (methods.length != 2) {
                throw new IllegalArgumentException("accessorType has to declare 2 methods. " + clazz.getName() + " has " + methods.length);
            }

            Method getter;
            Method setter;

            if (methods[0].getParameterTypes().length == 0) {
                getter = methods[0];
                setter = methods[1];
            } else {
                getter = methods[1];
                setter = methods[0];
            }

            Class<?> fieldType = getter.getReturnType();
            String fieldName = fieldType.getSimpleName().toLowerCase();
            String field = String.format("private %s %s;", fieldType.getName(), fieldName);

            CtField f1 = CtField.make(field, ctClass);
            ctClass.addField(f1);

            String getMethod = String.format("public %s %s(){return %s;}", fieldType.getName(), getter.getName(), fieldName);
            String setMethod = String.format("public void %s(%s %s){this.%s = %s;}", setter.getName(), fieldType.getName(), fieldName, fieldName, fieldName);

            CtMethod m1 = CtMethod.make(getMethod, ctClass);
            CtMethod m2 = CtMethod.make(setMethod, ctClass);
            ctClass.addMethod(m1);
            ctClass.addMethod(m2);
        } catch (Exception e) {
            LOG.warn(String.format("Add field %s error, message:", clazz.getName()), e);
        }
    }

    public abstract String getImplMethodName();
}
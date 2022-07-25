package com.demo.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class OtherHandler implements MethodInterceptor {

    private String name = "other";

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(name + " - before");
        Object ret = methodProxy.invokeSuper(o, objects);
        System.out.println(name + " - after");
        return ret;
    }
}

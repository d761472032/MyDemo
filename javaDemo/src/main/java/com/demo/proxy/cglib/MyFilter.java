package com.demo.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class MyFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if ("doSomething".equals(method.getName())) {
            return 0;
        }
        return 1;
    }

}

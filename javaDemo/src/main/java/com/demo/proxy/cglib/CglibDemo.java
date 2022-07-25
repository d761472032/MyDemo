package com.demo.proxy.cglib;

import net.sf.cglib.proxy.*;

public class CglibDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);

        /*
        enhancer.setCallback(new Handler());
        Target target = (Target) enhancer.create();
        System.out.println(target.doSomething("exec"));
        */

        /*
        enhancer.setCallbackType(Handler.class);
        Class<?> aClass = enhancer.createClass();
        Enhancer.registerCallbacks(aClass, new Callback[] { new Handler() });
        Target target = (Target) aClass.newInstance();
        System.out.println(target.doSomething("exec"));
        */

        enhancer.setCallbackFilter(new MyFilter());
        enhancer.setCallbackTypes(new Class<?>[] {Handler.class, OtherHandler.class});
        Class<?> aClass = enhancer.createClass();
        Object o = aClass.newInstance();
        ((Factory) o).setCallbacks(new Callback[] { new Handler(), new OtherHandler() });
        System.out.println(((Target) o).doSomething("exec"));
        System.out.println(((Target) o).doSomething2("exec"));
    }

}

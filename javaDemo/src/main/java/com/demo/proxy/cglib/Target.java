package com.demo.proxy.cglib;

public class Target {

    public String doSomething(String thing) {
        System.out.println("this is one");
        return thing;
    }

    public String doSomething2(String thing) {
        System.out.println("this is two");
        return thing;
    }

}

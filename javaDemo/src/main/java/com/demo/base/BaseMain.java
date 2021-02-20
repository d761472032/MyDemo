package com.demo.base;

import org.openjdk.jol.info.ClassLayout;

public class BaseMain {

    static class A {
        double flag;
    }

    public static void main(String[] args) {
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }

}

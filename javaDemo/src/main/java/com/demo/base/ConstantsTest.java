package com.demo.base;

public class ConstantsTest {

    public static void main(String[] args) {
        String s = "hello2";    // s指向的是常量池中的hello2
        final String s2 = "hello";  // 经过final修饰的s2在编译器也会被解析为常量放到常量池
        String s3 = s2 + 2; // s2+2相当于两个常量相加，结果还是常量，所以s3也指向常量池中的hello2
        System.out.println(s == s3);
    }

}

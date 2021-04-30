package com.demo.binaray;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Test {

    public static void main(String[] args) {
        try {
            // 类库池, jvm中所加载的class
            ClassPool pool = ClassPool.getDefault();
            // 获取指定的Student类
            CtClass ctClass = pool.get("com.demo.binaray.Student");
            // 获取sayHello方法
            CtMethod ctMethod = ctClass.getDeclaredMethod("a");
            // 在方法的代码后追加 一段代码
            ctMethod.insertAfter("System.out.println(\"new\");");
            // 使用当前的ClassLoader加载被修改后的类
            Class<Student> newClass = (Class<Student>) ctClass.toClass();
            Student stu = newClass.newInstance();
            stu.a();
            ctClass.writeFile();
        } catch (Exception e) {
        }
    }

}

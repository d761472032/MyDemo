package com.demo.annotation;

import com.demo.denum.FormModelType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormModel {
    FormModelType type();
    Class clazz();
    String key();
}

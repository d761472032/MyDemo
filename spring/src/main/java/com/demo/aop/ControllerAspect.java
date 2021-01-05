package com.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

    /**
     * 切点表达式，只是作为一个标识，供通知使用
     */
    @Pointcut("execution(* com.demo.controller.DataController.list(..))")
    public void point() {}

    /**
     * 通知方法会在目标方法调用之前执行
     * execution(返回类型 包路径.类.方法(参数))  &&  within  &&  bean
     */
    @Before("point()")
    public void controllerBefore() {
        System.out.println("point cut @Before execute");
    }

    /**
     * 通知方法会在目标方法返回或异常后调用
     */
    @After("execution(* com.demo.controller.DataController.list(..))")
    public void controllerAfter() {
        System.out.println("point cut @After execute");
    }

    /**
     * 通知方法会在目标方法返回后调用
     */
    @AfterReturning("execution(* com.demo.controller.DataController.list(..))")
    public void controllerAfterReturning() {
        System.out.println("point cut @AfterReturning execute");
    }

    /**
     * 通知方法会在目标方法抛出异常后调用
     */
    @AfterThrowing("execution(* com.demo.controller.DataController.list(..))")
    public void controllerAfterThrowing() {
        System.out.println("point cut @AfterThrowing execute");
    }

    /**
     * 通知方法会将目标方法封装起来
     */
    @Around("execution(* com.demo.controller.DataController.list(..))")
    public Object controllerAround(ProceedingJoinPoint joinPoint) {
        try {
            System.out.println("point cut @Around execute before");
            return joinPoint.proceed();
        } catch (Throwable  throwable) {
            System.out.println(throwable.getMessage());
        } finally {
            System.out.println("point cut @Around execute after");
        }
        return null;
    }

    @Pointcut("execution(String com.demo.controller.DataController.getArgs(String)) && args(args)")
    public void point2(String args) {}

    @Around(value = "point2(args)", argNames = "joinPoint, args")
    public Object controllerArgsAround(ProceedingJoinPoint joinPoint, String args) {
        try {
            System.out.println("point cut @Around execute before");
            System.out.println(args);
            return joinPoint.proceed();
        } catch (Throwable  throwable) {
            System.out.println(throwable.getMessage());
        } finally {
            System.out.println("point cut @Around execute after");
        }
        return null;
    }

}

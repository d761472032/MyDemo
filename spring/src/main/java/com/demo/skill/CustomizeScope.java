package com.demo.skill;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * spring默认支持的Scope只有两种：
 *  1.singleton 单例，每次从spring容器中获取到的bean都是同一个对象。
 *  2.prototype 多例，每次从spring容器中获取到的bean都是不同的对象。
 * spring web又对Scope进行了扩展，增加了：
 *  1.RequestScope 同一次请求从spring容器中获取到的bean都是同一个对象。
 *  2.SessionScope 同一个会话从spring容器中获取到的bean都是同一个对象。
 *
 *  自定义场景：我们想在同一个线程中从spring容器获取到的bean都是同一个对象
 */
public class CustomizeScope {

    /**
     * 第三步使用新定义的Scope
     */
    @Scope("threadLocalScope")
    public class CService {

        public void add() {
        }
    }

    /**
     * 第二步将新定义的Scope注入到spring容器中
     */
    @Component
    static public class ThreadLocalBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            beanFactory.registerScope("threadLocalScope", new ThreadLocalScope());
        }

    }

    /**
     * 第一步实现Scope接口
     */
    static public class ThreadLocalScope implements org.springframework.beans.factory.config.Scope {

        private static final ThreadLocal<Object> THREAD_LOCAL_SCOPE = new ThreadLocal<>();

        @Override
        public Object get(String s, ObjectFactory<?> objectFactory) {
            Object value = THREAD_LOCAL_SCOPE.get();
            if (value != null) {
                return value;
            }

            Object object = objectFactory.getObject();
            THREAD_LOCAL_SCOPE.set(object);
            return object;
        }

        @Override
        public Object remove(String s) {
            THREAD_LOCAL_SCOPE.remove();
            return null;
        }

        @Override
        public void registerDestructionCallback(String s, Runnable runnable) {
        }

        @Override
        public Object resolveContextualObject(String s) {
            return null;
        }

        @Override
        public String getConversationId() {
            return null;
        }
    }

}
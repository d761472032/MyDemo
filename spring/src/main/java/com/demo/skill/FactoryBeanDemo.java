package com.demo.skill;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * BeanFactory：spring容器的顶级接口，管理bean的工厂。
 * FactoryBean：并非普通的工厂bean，它隐藏了实例化一些复杂Bean的细节，给上层应用带来了便利。
 */
public class FactoryBeanDemo {

    /**
     * 获取FactoryBean实例对象
     */
    @Service
    static public class MyFactoryBeanService implements BeanFactoryAware {
        private BeanFactory beanFactory;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        }

        public void test() {
            // getBean("myFactoryBean");获取的是MyFactoryBeanService类中getObject方法返回的对象，
            Object myFactoryBean = beanFactory.getBean("myFactoryBean");
            System.out.println(myFactoryBean);

            // getBean("&myFactoryBean");获取的才是MyFactoryBean对象。
            Object myFactoryBean1 = beanFactory.getBean("&myFactoryBean");
            System.out.println(myFactoryBean1);
        }
    }

    /**
     * 定义FactoryBean
     */
    @Component
    static public class MyFactoryBean implements FactoryBean {

        @Override
        public Object getObject() throws Exception {
            String data1 = buildData1();
            String data2 = buildData2();
            return buildData3(data1, data2);
        }

        private String buildData1() {
            return "data1";
        }

        private String buildData2() {
            return "data2";
        }

        private String buildData3(String data1, String data2) {
            return data1 + data2;
        }

        @Override
        public Class<?> getObjectType() {
            return null;
        }
    }

}

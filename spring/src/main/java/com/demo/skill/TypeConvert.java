package com.demo.skill;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * spring目前支持3中类型转换器：
 *  1.Converter<S,T>：将 S 类型对象转为 T 类型对象
 *  2.ConverterFactory<S, R>：将 S 类型对象转为 R 类型及子类对象
 *  3.GenericConverter：它支持多个source和目标类型的转化，同时还提供了source和目标类型的上下文，这个上下文能让你实现基于属性上的注解或信息来进行类型转换。
 */
public class TypeConvert {

    /**
     * 第四步，调用接口
     */
    @RequestMapping("/user")
    @RestController
    static public class UserController {

        @RequestMapping("/save")
        public String save(@RequestBody User user) {
            return "success";
        }
    }

    /**
     * 第三步，将新定义的类型转换器注入到spring容器中
     */
    @Configuration
    static public class WebConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new DateConverter());
        }
    }

    /**
     * 第二步，实现Converter接口
     */
    static public class DateConverter implements Converter<String, Date> {

        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Date convert(String source) {
            if (source != null && !"".equals(source)) {
                try {
                    simpleDateFormat.parse(source);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * 第一步，定义一个实体User
     */
    @Data
    static public class User {
        private Long id;
        private String name;
        private Date registerDate;
    }

}

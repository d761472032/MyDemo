package com.demo.resolver;

import com.demo.annotation.FormModel;
import com.demo.denum.FormModelType;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Component
public class FormModelHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(FormModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        FormModel formModel = methodParameter.getParameterAnnotation(FormModel.class);
        FormModelType type = Optional.ofNullable(formModel).map(FormModel::type).orElse(FormModelType.OBJECT);
        Class<?> clazz = Optional.ofNullable(formModel).map(FormModel::clazz).orElse(Object.class); // clazz.isPrimitive()
        String key = Optional.ofNullable(formModel).map(FormModel::key).orElse("");

        // 解析结果对象
        Object obj = type == FormModelType.OBJECT ? clazz.newInstance() : new ArrayList<>();
        // 前端提交name值
        String frontKey;
        // 前端提交参数值
        String[] values;
        // 构建对象方法名
        String methodName;
        // 前端所有参数
        Map<String, String[]> parameterMap = nativeWebRequest.getParameterMap();
        // 返回对象字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 拼接前端参数key
            frontKey = String.format("%s.%s", key, field.getName());
            if (parameterMap.containsKey(frontKey)) {
                values = parameterMap.get(frontKey);
                methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                if (type == FormModelType.OBJECT) {
                    clazz.getDeclaredMethod(methodName, field.getType()).invoke(obj, values[0]);
                } else {
                    Object tmp;
                    for (String value : values) {
                        tmp = clazz.newInstance();
                        clazz.getDeclaredMethod(methodName, field.getType()).invoke(tmp, value);
                        ((ArrayList<Object>)obj).add(tmp);
                    }
                }
            }
        }

        return obj;
    }

}

package com.wh.core.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils {
    public static <T> T getT(Object o, int i) {
        try {

            Type type = o.getClass().getGenericSuperclass(); // generic 泛型
            // 强制转化“参数化类型”
            ParameterizedType parameterizedType = (ParameterizedType) type;
            // 参数化类型中可能有多个泛型参数
            Type[] types = parameterizedType.getActualTypeArguments();
            // 获取数据的第一个元素(User.class)
            return ((Class<T>) types[i]).newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

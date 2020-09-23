package com.wh.core.common.util;

import android.app.Activity;

import com.wh.core.WHActivity;
import com.wh.core.inject.ViewId;
import com.wh.core.mvvm.BaseViewRender;

import java.lang.reflect.Field;

public class InjectManager {

    public static void inject(WHActivity activity) {
        injectViewId(activity);
    }

    private static void injectViewId(WHActivity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        //查询当前的所有的字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewId.class)) {
                ViewId inject = field.getAnnotation(ViewId.class);
                int id = inject.value();
                if (id > 0) {
                    field.setAccessible(true);
                    try {
                        field.set(activity, activity.findViewById(id));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static void inject(BaseViewRender render) {
        injectViewId(render);
    }

    private static void injectViewId(BaseViewRender render) {
        Class<? extends BaseViewRender> clazz = render.getClass();
        //查询当前的所有的字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewId.class)) {
                ViewId inject = field.getAnnotation(ViewId.class);
                int id = inject.value();
                if (id > 0) {
                    field.setAccessible(true);
                    try {
                        field.set(render, render.findViewById(id));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}

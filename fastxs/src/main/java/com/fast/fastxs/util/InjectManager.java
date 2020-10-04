package com.fast.fastxs.util;

import android.app.Activity;

import com.fast.fastxs.inject.ViewId;
import com.fast.fastxs.mvvm.XsBaseViewRender;

import java.lang.reflect.Field;

public class InjectManager {

    public static void inject(Activity activity) {
        injectViewId(activity);
    }

    private static void injectViewId(Activity activity) {
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

    public static void inject(XsBaseViewRender render) {
        injectViewId(render);
    }

    private static void injectViewId(XsBaseViewRender render) {
        Class<? extends XsBaseViewRender> clazz = render.getClass();
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

package com.example.yinpengcheng.annotationdemo;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by yinpengcheng on 2018/2/10.
 */

public class ViewUtils {
    private ViewUtils() {
    }
    public static void inject(Activity activity){
        injectObject(activity, new ViewFinder(activity));
    }

    private static void injectObject(Object handler, ViewFinder finder){
        Class<?> handlerType = handler.getClass();
        Field[] fields = handlerType.getDeclaredFields();
        if (fields != null && fields.length > 0){
            for (Field field:fields){
                ViewInject viewInject = field.getAnnotation(ViewInject.class);
                if (viewInject != null) {
                    try {
                        View view = finder.findViewById(viewInject.value(), viewInject.parentId());
                        if (view != null) {
                            field.setAccessible(true);
                            field.set(handler, view);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}

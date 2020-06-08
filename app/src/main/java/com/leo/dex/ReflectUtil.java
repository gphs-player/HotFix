package com.leo.dex;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>Date:2020-04-02.21:11</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class ReflectUtil {
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class<?> aClass = instance.getClass();
        while (aClass != null) {

            try {
                Field field = aClass.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                aClass = aClass.getSuperclass();
            }
        }
        throw new NoSuchFieldException("No such field : " + name);
    }

    public static Method findMethod(Object instance, String name, Class<?> ...params) throws NoSuchMethodException {
        Class<?> aClass = instance.getClass();
        while (aClass != null) {

            try {
                Method method = aClass.getDeclaredMethod(name, params);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                aClass = aClass.getSuperclass();
            }
        }
        throw new NoSuchMethodException("No such method :" + name);
    }
}

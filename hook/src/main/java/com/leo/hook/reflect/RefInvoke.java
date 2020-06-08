package com.leo.hook.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>Date:2020/6/4.3:07 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class RefInvoke {

    public static Object createObject(Class clazz, Class[] paramTypes, Object[] args) {
        Constructor<?> constructor;
        try {
            constructor = clazz.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射构造一个对象的实例
     */
    public static Object createObject(String className, Class[] paramTypes, Object[] args) {
        try {
            Class<?> aClass = Class.forName(className);
            return createObject(aClass, paramTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object createObject(String className, Class paramTypes, Object args) {
        Class[] p = new Class[]{paramTypes};
        Object[] a = new Object[]{args};
        try {
            return createObject(className, p, a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用对象实例方法
     */
    public static Object invokeInstanceMethod(Object obj, String methodName, Class[] paramTypes, Object[] args) {
        if (obj == null) return null;
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(methodName, paramTypes);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用类静态方法
     */
    public static Object invokeStaticMethod(String className, String method, Class[] paramsType, Object[] args) {
        try {
            Class<?> aClass = Class.forName(className);
            Method declaredMethod = aClass.getDeclaredMethod(method, paramsType);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object getFieldObject(Object obj, String fieldName) {
        return getFieldObject(obj.getClass(), obj, fieldName);
    }

    public static Object getFieldObject(String className, Object obj, String fieldName) {

        try {
            return getFieldObject(Class.forName(className), obj, fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射获取字段的值
     */
    public static Object getFieldObject(Class className, Object obj, String fieldName) {
        try {
            Field declaredField = className.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射获取静态字段的值
     */
    public static Object getStaticFieldObject(String className, String fieldName) {
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
            return getFieldObject(aClass, null, fieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射设置静态字段的值
     */
    public static void setStaticFieldObject(String className, String fieldName, Object value) {
        setFieldObject(className, null, fieldName, value);

    }

    public static void setFieldObject(String className, Object obj, String fieldName, Object value) {
        try {
            Class<?> aClass = Class.forName(className);
            setFieldObject(aClass, obj, fieldName, value);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldObject(Class className, Object obj, String fieldName, Object value) {
        try {
            Field field = className.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldObject(Object obj, String fieldName, Object value) {
        try {
            Class<?> aClass = obj.getClass();
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

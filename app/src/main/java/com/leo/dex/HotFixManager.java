package com.leo.dex;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Date:2020-04-02.21:17</p>
 * <p>Author:niu bao</p>
 * <p>Desc:</p>
 */
public class HotFixManager {
    private static final String PATHC_DIR = Environment.getExternalStorageDirectory().getPath() + "/hotfix.dex";

    public static void installPatchDex(Context context) {
        File file = new File(PATHC_DIR);
        if (!file.exists()) {
            //补丁不存在，返回
            return;
        }
        try {

            ClassLoader loader = context.getClassLoader();
            //dalvik.system.PathClassLoader
            System.out.println(loader.getClass().getName());
            //1.获取pathList对象实例
            Field pathListField = ReflectUtil.findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);


            //2.获取makeDexElements方法
            Method makeDexElements = ReflectUtil.findMethod(dexPathList, "makeDexElements"
                    , List.class
                    , File.class
                    , List.class
                    , ClassLoader.class);
            //3.参考makeDexElements方法准备参数
//            private static Element[] makeDexElements(List<File> files, File optimizedDirectory,
//                    List<IOException> suppressedExceptions, ClassLoader loader) {
//                return makeDexElements(files, optimizedDirectory, suppressedExceptions, loader, false);
//            }
            List<File> filesToBeInstalled = new ArrayList<>();
            filesToBeInstalled.add(file);
            File optimizedDirectory = context.getDir("fixed_dex",Context.MODE_APPEND);
            if (!optimizedDirectory.exists()){
                optimizedDirectory.mkdirs();
            }
            List<IOException> exceptions = new ArrayList<>();
            //4.反射调用makeDexElements方法得到解析的dex数组
            Object[] extraElements = (Object[]) makeDexElements.invoke(dexPathList
                    , filesToBeInstalled
                    , optimizedDirectory
                    , exceptions
                    , loader);
            //5得到系统原始的Elements数组
            Field dexElementsField = ReflectUtil.findField(dexPathList, "dexElements");
            Object[] originalElements = (Object[]) dexElementsField.get(dexPathList);
            //6.合并数组,先存放patch的数组，确保系统先加载
            Object[] newElements = (Object[]) Array.newInstance(originalElements.getClass().getComponentType(), originalElements.length + extraElements.length);
            System.arraycopy(extraElements, 0, newElements, 0, extraElements.length);
            System.arraycopy(originalElements, 0, newElements, extraElements.length, originalElements.length);

            //将结果存放到系统
            dexElementsField.set(dexPathList, newElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

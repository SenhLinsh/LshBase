package com.linsh.base;

import com.linsh.base.variable.DefaultVariableManager;
import com.linsh.base.variable.IVariableManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/22
 *    desc   :
 * </pre>
 */
public class LshVariable {

    private static IVariableManager manager = new DefaultVariableManager();

    public static <T> T get(String key) {
        return manager.get(key);
    }

    public static <T> T get(String key, Class<T> classOfT) {
        return manager.get(key, classOfT);
    }

    public static <T> T get(Class<T> classOfT) {
        return manager.get(classOfT);
    }

    public static <T> void put(String key, T value) {
        manager.put(key, value);
    }

    public static <T> void put(T value) {
        manager.put(value);
    }

    public static void remove(String key) {
        manager.remove(key);
    }

    public static <T> void remove(Class<T> classOfT) {
        manager.remove(classOfT);
    }
}

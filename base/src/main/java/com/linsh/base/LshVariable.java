package com.linsh.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    private static final IVariableManager manager = new DefaultVariableManager();

    /**
     * 获取变量
     *
     * @param key key
     * @return 变量值, 变量不存在时返回 null
     */
    @Nullable
    public static <T> T get(@NonNull String key) {
        return manager.get(key);
    }


    /**
     * 获取变量
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return 变量值, 变量不存在时返回默认值
     */
    @NonNull
    public static <T> T get(@NonNull String key, @NonNull T defaultValue) {
        return manager.get(key, defaultValue);
    }

    /**
     * 获取变量
     *
     * @param classOfT 以变量对应的类字节码作为 key
     * @return 变量值, 变量不存在时返回 null
     */
    @Nullable
    public static <T> T get(@NonNull Class<T> classOfT) {
        return manager.get(classOfT);
    }

    /**
     * 存储变量
     *
     * @param key   key
     * @param value 变量值
     */
    public static <T> void put(@NonNull String key, T value) {
        manager.put(key, value);
    }

    /**
     * 存储变量
     *
     * @param classOfT 以变量对应的类字节码作为 key
     * @param value    变量值
     */
    public static <T> void put(@NonNull Class<T> classOfT, T value) {
        manager.put(classOfT, value);
    }

    /**
     * 存储变量
     *
     * @param value 变量值, 默认以当前变量对应的类字节码作为 key
     */
    public static <T> void put(@NonNull T value) {
        manager.put(value);
    }

    /**
     * 移除变量
     *
     * @param key key
     */
    public static void remove(@NonNull String key) {
        manager.remove(key);
    }

    /**
     * 移除变量
     *
     * @param classOfT 以变量对应的类字节码作为 key
     */
    public static <T> void remove(@NonNull Class<T> classOfT) {
        manager.remove(classOfT);
    }

    /**
     * 订阅变量更新通知
     *
     * @param key        key
     * @param subscriber 订阅者, 用于接收更新通知
     */
    public static <T> void subscribe(@NonNull String key, @NonNull IVariableManager.Subscriber<T> subscriber) {
        manager.subscribe(key, subscriber);
    }

    /**
     * 订阅变量更新通知
     *
     * @param classOfT   以变量对应的类字节码作为 key
     * @param subscriber 订阅者, 用于接收更新通知
     */
    public static <T> void subscribe(@NonNull Class<T> classOfT, @NonNull IVariableManager.Subscriber<T> subscriber) {
        manager.subscribe(classOfT, subscriber);
    }
}

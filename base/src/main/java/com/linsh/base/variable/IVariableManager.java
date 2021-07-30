package com.linsh.base.variable;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/22
 *    desc   :
 * </pre>
 */
public interface IVariableManager {

    /**
     * 获取变量
     *
     * @param key key
     * @return 变量值, 变量不存在时返回 null
     */
    @Nullable
    <T> T get(String key);

    /**
     * 获取变量
     *
     * @param classOfT 以变量对应的类字节码作为 key
     * @return 变量值, 变量不存在时返回 null
     */
    @Nullable
    <T> T get(Class<T> classOfT);

    /**
     * 存储变量
     *
     * @param key   key
     * @param value 变量值
     */
    <T> void put(String key, T value);

    /**
     * 存储变量
     *
     * @param classOfT 以变量对应的类字节码作为 key
     * @param value    变量值
     */
    <T> void put(Class<T> classOfT, T value);

    /**
     * 存储变量
     *
     * @param value 变量值, 默认以当前变量对应的类字节码作为 key
     */
    <T> void put(T value);

    /**
     * 移除变量
     *
     * @param key key
     */
    void remove(String key);

    /**
     * 移除变量
     *
     * @param classOfT 以变量对应的类字节码作为 key
     */
    <T> void remove(Class<T> classOfT);

    /**
     * 订阅变量更新通知
     *
     * @param key        key
     * @param subscriber 订阅者, 用于接收更新通知
     */
    <T> void subscribe(String key, Subscriber<T> subscriber);

    /**
     * 订阅变量更新通知
     *
     * @param classOfT   以变量对应的类字节码作为 key
     * @param subscriber 订阅者, 用于接收更新通知
     */
    <T> void subscribe(Class<T> classOfT, Subscriber<T> subscriber);

    /**
     * 订阅器, 用于接收更新通知
     */
    interface Subscriber<T> {

        void accept(T t);
    }
}

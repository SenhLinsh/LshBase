package com.linsh.base.variable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/22
 *    desc   :
 * </pre>
 */
public interface IVariableManager {

    <T> T get(String key);

    <T> T get(String key, Class<T> classOfT);

    <T> T get(Class<T> classOfT);

    <T> void put(String key, T value);

    <T> void put(T value);

    void remove(String key);

    <T> void remove(Class<T> classOfT);
}

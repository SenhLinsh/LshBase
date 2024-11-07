package com.linsh.base.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2021/11/15
 *    desc   : Properties 文件声明类
 *
 *             该文件类型目前用于：
 *                 1. 朝花夕逝图片信息
 * </pre>
 */
public interface IProperties {

    void put(String key, String value);

    void put(String key, int value);

    void put(String key, long value);

    void put(String key, String[] value);

    void put(String key, List<?> value);

    void putTags(String key, String[] value);

    void putTags(String key, List<?> value);

    String get(String key);

    int getInt(String key);

    long getLong(String key);

    String[] getArray(String key);

    String[] getArrayAsTag(String key);

    List<String> getList(String key);

    List<String> getListAsTag(String key);

    Set<Map.Entry<String, String>> entrySet();

    String remove(String key);
}

package com.linsh.base.variable;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/22
 *    desc   :
 * </pre>
 */
public class DefaultVariableManager implements IVariableManager {

    private Map<String, Object> variables = new HashMap<>();

    @Override
    public <T> T get(String key) {
        return (T) variables.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> classOfT) {
        Object value = variables.get(key);
        if (value != null && value.getClass() == classOfT) {
            return (T) value;
        }
        return null;
    }

    @Override
    public <T> T get(Class<T> classOfT) {
        Object value = variables.get(classOfT.getName());
        if (value != null && value.getClass() == classOfT) {
            return (T) value;
        }
        return null;
    }

    @Override
    public <T> void put(String key, T value) {
        variables.put(key, value);
    }

    @Override
    public <T> void put(T value) {
        variables.put(value.getClass().getName(), value);
    }

    @Override
    public void remove(String key) {
        variables.remove(key);
    }

    @Override
    public <T> void remove(Class<T> classOfT) {
        variables.remove(classOfT.getName());
    }
}

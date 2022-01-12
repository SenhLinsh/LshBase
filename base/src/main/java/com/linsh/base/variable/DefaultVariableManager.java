package com.linsh.base.variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/22
 *    desc   :
 * </pre>
 */
public class DefaultVariableManager implements IVariableManager {

    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, Set<Subscriber<?>>> subscribersMap = new HashMap<>();

    @Override
    public <T> T get(String key) {
        return (T) variables.get(key);
    }

    @Override
    public <T> T get(Class<T> classOfT) {
        Object value = variables.get(classOfT.getName());
        if (value != null && classOfT.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    @Override
    public <T> void put(String key, T value) {
        variables.put(key, value);
        notifySubscribers(key, value);
    }

    @Override
    public <T> void put(Class<T> classOfT, T value) {
        put(classOfT.getName(), value);
    }

    @Override
    public <T> void put(T value) {
        put(value.getClass().getName(), value);
    }

    @Override
    public void remove(String key) {
        variables.remove(key);
        notifySubscribers(key, null);
    }

    @Override
    public <T> void remove(Class<T> classOfT) {
        remove(classOfT.getName());
    }

    @Override
    public <T> void subscribe(String key, Subscriber<T> subscriber) {
        Set<Subscriber<?>> subscribers = this.subscribersMap.get(key);
        if (subscribers == null) {
            subscribers = new HashSet<>();
            subscribersMap.put(key, subscribers);
        }
        subscriber.accept((T) variables.get(key));
    }

    @Override
    public <T> void subscribe(Class<T> classOfT, Subscriber<T> subscriber) {
        subscribe(classOfT.getName(), subscriber);
    }

    private void notifySubscribers(String key, Object value) {
        if (subscribersMap.size() > 0) {
            Set<Subscriber<?>> subscribers = this.subscribersMap.get(key);
            if (subscribers != null) {
                for (Subscriber subscriber : subscribers) {
                    subscriber.accept(value);
                }
            }
        }
    }
}

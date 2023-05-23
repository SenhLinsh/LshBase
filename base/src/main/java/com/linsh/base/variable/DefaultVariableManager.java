package com.linsh.base.variable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    public <T> T get(@NonNull String key) {
        return (T) variables.get(key);
    }

    @Nullable
    @Override
    public <T> T get(@NonNull String key, @NonNull T defaultValue) {
        Object value = variables.get(key);
        if (value != null && defaultValue.getClass().isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return defaultValue;
    }

    @Override
    public <T> T get(@NonNull Class<T> classOfT) {
        Object value = variables.get(classOfT.getName());
        if (value != null && classOfT.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    @Override
    public <T> void put(@NonNull String key, T value) {
        variables.put(key, value);
        notifySubscribers(key, value);
    }

    @Override
    public <T> void put(@NonNull Class<T> classOfT, T value) {
        put(classOfT.getName(), value);
    }

    @Override
    public <T> void put(@NonNull T value) {
        put(value.getClass().getName(), value);
    }

    @Override
    public void remove(@NonNull String key) {
        variables.remove(key);
        notifySubscribers(key, null);
    }

    @Override
    public <T> void remove(@NonNull Class<T> classOfT) {
        remove(classOfT.getName());
    }

    @Override
    public <T> void subscribe(@NonNull String key, @NonNull Subscriber<T> subscriber) {
        Set<Subscriber<?>> subscribers = this.subscribersMap.get(key);
        if (subscribers == null) {
            subscribers = new HashSet<>();
            subscribersMap.put(key, subscribers);
        }
        subscriber.accept((T) variables.get(key));
    }

    @Override
    public <T> void subscribe(@NonNull Class<T> classOfT, @NonNull Subscriber<T> subscriber) {
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

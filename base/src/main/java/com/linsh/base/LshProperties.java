package com.linsh.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linsh.base.property.IProperties;
import com.linsh.register.InterfaceRegisters;

import java.lang.reflect.Constructor;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/05/30
 *    desc   : Properties 配置文件管理及校验
 *
 *
 * </pre>
 */
public class LshProperties {

    private static final IProperties instance;

    static {
        Class<? extends IProperties> implClazz = InterfaceRegisters.findRegisters(IProperties.class).get(0);
        try {
            Constructor<? extends IProperties> constructor = implClazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("init implement of IProperties error: " + implClazz, e);
        }
    }

    private LshProperties() {
    }

    public static String get(@NonNull String key) {
        return instance.get(key);
    }

    public static String get(@NonNull String key, @Nullable String def) {
        return instance.get(key, def);
    }

    public static int getInt(@NonNull String key, int def) {
        return instance.getInt(key, def);
    }

    public static long getLong(@NonNull String key, long def) {
        return instance.getLong(key, def);
    }

    public static boolean getBoolean(@NonNull String key, boolean def) {
        return instance.getBoolean(key, def);
    }

    public static void set(@NonNull String key, @Nullable String val) {
        instance.set(key, val);
    }
}

package com.linsh.base.property;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/05/30
 *    desc   : Properties 配置文件接口
 * </pre>
 */
public interface IProperties {

    String get(@NonNull String key);

    String get(@NonNull String key, @Nullable String def);

    int getInt(@NonNull String key, int def);

    long getLong(@NonNull String key, long def);

    boolean getBoolean(@NonNull String key, boolean def);

    void set(@NonNull String key, @Nullable String val);
}

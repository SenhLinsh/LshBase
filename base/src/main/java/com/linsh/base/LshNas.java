package com.linsh.base;

import com.linsh.base.net.nas.NasManager;
import com.linsh.base.net.nas.impl.DefaultNasManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/06
 *    desc   : Nas 服务器模块
 * </pre>
 */
public class LshNas {

    private static NasManager instance;

    /**
     * 获取默认的 NasManager
     */
    public static NasManager getDefault() {
        if (instance == null)
            throw new NullPointerException("instance == null");
        return instance;
    }

    /**
     * 设置默认的 NasManager
     */
    public static NasManager setDefault(NasManager.Builder builder) {
        return instance = builder.build();
    }

    /**
     * 为 NasManager 构建一个 Builder
     */
    public static NasManager.Builder builder() {
        return new DefaultNasManager.Builder();
    }
}

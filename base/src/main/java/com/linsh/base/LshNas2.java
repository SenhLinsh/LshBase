package com.linsh.base;

import com.linsh.base.net.nas2.INasManager;
import com.linsh.base.net.nas2.impl.SmbjNasManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/06
 *    desc   : Nas 服务器模块
 *
 *             当前为第二套接口，由于新方案的接口不方便兼容第一套模式，为了不过多损失性能，在此重新增加一套接口。
 *
 *             当前接口实现：https://github.com/hierynomus/smbj
 * </pre>
 */
public class LshNas2 {

    private static INasManager instance;

    /**
     * 获取默认的 INasManager
     */
    public static INasManager getDefault() {
        if (instance == null)
            throw new NullPointerException("instance == null");
        return instance;
    }

    /**
     * 设置默认的 NasManager
     */
    public static INasManager setDefault(INasManager.Builder builder) {
        return instance = builder.build();
    }

    /**
     * 为 NasManager 构建一个 Builder
     */
    public static INasManager.Builder builder() {
        return new SmbjNasManager.Builder();
    }
}

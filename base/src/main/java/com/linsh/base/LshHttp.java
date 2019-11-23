package com.linsh.base;


import com.linsh.base.config.HttpConfig;
import com.linsh.base.net.http.HttpManager;
import com.linsh.base.net.http.retrofit.RetrofitManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/03/28
 *    desc   : 网络库入口, 使用代理模式来实现 HttpManager 接口. 目前代理了 RetrofitManager 的实现逻辑.
 * </pre>
 */
public class LshHttp {

    // 默认实例, 用于缓存默认常用对象, 减少初始化操作
    private static HttpManager instance;

    private LshHttp() {
    }

    /**
     * 设置网络默认配置
     *
     * @param config 网络配置对象
     */
    public static void setDefaultConfig(HttpConfig config) {
        instance = new RetrofitManager(config);
    }

    /**
     * 获取默认的实例, 没有设置默认实例时, 将使用配置进行初始化
     */
    public static HttpManager getDefaultInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("请先通过 setDefaultConfig 设置默认值");
        }
        return instance;
    }

    /**
     * 获取 Http Manager 的实例
     *
     * @param config http 配置
     */
    public static HttpManager getInstance(HttpConfig config) {
        return new RetrofitManager(config);
    }
}

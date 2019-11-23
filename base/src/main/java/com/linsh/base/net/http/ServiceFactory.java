package com.linsh.base.net.http;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : 提供 API 服务接口实例的工厂
 *
 *             该 API 接口的实例暂时参考 Retrofit 的使用方式, 且目前仅供 Retrofit 的实现方式使用.
 * </pre>
 */
public interface ServiceFactory {

    /**
     * @param service 提供 API 接口的类(interface)
     * @return API 接口类的实例
     */
    <T> T create(Class<T> service);
}

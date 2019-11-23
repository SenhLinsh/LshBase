package com.linsh.base.net.http;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/11
 *    desc   : 提供统一的 http 请求接口
 * </pre>
 */
public interface HttpManager {

    /**
     * 构建 API 接口实例来进行请求
     *
     * @param clazz API 接口
     * @return 接口实例
     */
    <T> T service(Class<T> clazz);

    /**
     * 通过请求构造器来进行 Get 请求
     *
     * @param url Url 链接
     * @return Get 请求构造器
     */
    GetBuilder get(String url);

    /**
     * 通过请求构造器来进行 Post 请求
     *
     * @param url Url 链接
     * @return Post 请求构造器
     */
    PostBuilder post(String url);
}

package com.linsh.base.net.http;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : 请求构造器的工厂
 * </pre>
 */
public interface RequestBuilderFactory {

    /**
     * @return Get 请求的构造器
     */
    GetBuilder get(String url);

    /**
     * @return Post 请求的构造器
     */
    PostBuilder post(String url);
}

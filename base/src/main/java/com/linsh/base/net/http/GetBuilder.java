package com.linsh.base.net.http;

import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : Get 请求构造器的接口
 * </pre>
 */
public interface GetBuilder extends RequestBuilder<GetBuilder> {

    /**
     * 添加参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    GetBuilder addParam(String name, String value);

    /**
     * 添加参数
     *
     * @param params 参数集合
     */
    GetBuilder addParams(Map<String, String> params);

    /**
     * 移除参数
     *
     * @param name 参数名
     */
    GetBuilder removeParam(String name);
}

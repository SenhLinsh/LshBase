package com.linsh.base.net.http;

import java.io.IOException;

import okhttp3.Call;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   : Call 请求对象, 用于发起请求
 * </pre>
 */
public interface RequestCall extends Call {

    /**
     * 执行同步请求, 根据指定的返回类型, 作自动转换
     * 注: 需要确保 Converter Factory 可以提供合适的转换器
     *
     * @param classOfT 返回类型的字节码对象
     * @return 指定的返回类型
     */
    <T> T execute(Class<T> classOfT) throws IOException;

    /**
     * 异步执行请求, 根据指定的返回类型, 作自动转换
     * 注: 需要确保 Converter Factory 可以提供合适的转换器
     *
     * @param callback 支持自动转换的回调, 框架会自动检测该回调中指定的泛型类型, 并寻找合适的转换器进行转换
     */
    <T> void enqueue(AdapterCallback<T> callback);
}

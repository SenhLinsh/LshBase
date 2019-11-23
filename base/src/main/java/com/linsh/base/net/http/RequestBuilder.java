package com.linsh.base.net.http;


import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : 请求构造器接口, 用于构造请求
 * </pre>
 */
public interface RequestBuilder<T extends RequestBuilder> {

    /**
     * 添加请求头
     */
    T addHeader(String name, String value);

    /**
     * 移除请求头
     */
    T removeHeader(String name);

    /**
     * 构建 Call 对象, 用于发起请求
     */
    RequestCall buildCall();

    /**
     * 构建 RxJava 事件流, 链式调用
     *
     * @param clazz 需要最终转换得到的 Flowable 流的对象的字节码
     * @return 以 Flowable 形式构建的 RxJava 事件流
     */
    <R> Flowable<R> buildFlowable(Class<R> clazz);

    /**
     * 构建 RxJava 事件流, 链式调用
     *
     * @param clazz 需要最终转换得到的 Flowable 流的对象的字节码
     * @return 以 Observable 形式构建的 RxJava 事件流
     */
    <R> Observable<R> buildObservable(Class<R> clazz);
}

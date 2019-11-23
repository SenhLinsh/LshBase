package com.linsh.base.common;

import io.reactivex.Flowable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/12
 *    desc   : 任务持有器接口
 *
 *             用于将任务调度到指定的线程进行操作
 * </pre>
 */
public interface Task<T> {

    /**
     * 同步执行
     */
    T sync();

    /**
     * 异步执行 (无回调)
     * <p>
     * 需指定异步执行的线程
     */
    void async();

    /**
     * 异步执行
     * <p>
     * 需指定异步执行的线程和回调线程
     *
     * @param callback 回调
     */
    void async(Callback<T> callback);

    /**
     * 返回 RxJava 形式
     */
    Flowable<T> flowable();
}

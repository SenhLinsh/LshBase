package com.linsh.base.mvp;

import com.linsh.base.thread.ThreadPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/18
 *    desc   : MVP 线程选择
 *
 *             通过该注解来选择指定的线程来运行 MVP 方法
 *
 *             注:
 *             1. 默认情况下, Presenter 方法执行的线程为 Presenter 线程; View 方法执行的线程未 UI 线程;
 *             2. 具有返回值的方法, 无法切换线程, 只能在调用线程进行方法执行.
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MvpThread {

    ThreadPolicy value();
}

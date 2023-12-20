package com.linsh.base.adapter;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
public interface IObservableRcvAdapter {

    <T extends RcvAdapterSubscribe> T subscribe(Class<T> classOfSubscriber);

    <T extends RcvAdapterSubscribe> T subscribe(T subscriber);

    boolean isSubscribed(Class<? extends RcvAdapterSubscribe> classOfSubscriber);

    boolean isSubscribed(RcvAdapterSubscribe subscriber);

    boolean unsubscribe(Class<? extends RcvAdapterSubscribe> classOfSubscriber);

    boolean unsubscribe(RcvAdapterSubscribe subscriber);
}

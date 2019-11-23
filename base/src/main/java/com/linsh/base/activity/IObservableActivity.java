package com.linsh.base.activity;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
public interface IObservableActivity {

    <T extends ActivitySubscribe> T subscribe(Class<T> classOfSubscriber);

    <T extends ActivitySubscribe> T subscribe(T subscriber);

    boolean isSubscribed(Class<? extends ActivitySubscribe> classOfSubscriber);

    boolean isSubscribed(ActivitySubscribe subscriber);

    boolean unsubscribe(Class<? extends ActivitySubscribe> classOfSubscriber);

    boolean unsubscribe(ActivitySubscribe subscriber);
}

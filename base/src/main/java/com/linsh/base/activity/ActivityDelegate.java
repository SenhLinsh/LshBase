package com.linsh.base.activity;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/06
 *    desc   :
 * </pre>
 */
public interface ActivityDelegate {

    ActivityDelegate subscribe(Class<? extends ActivitySubscribe> subscriber);

    ActivityDelegate subscribe(ActivitySubscribe subscriber);

    ActivityDelegate unsubscribe(Class<? extends ActivitySubscribe> subscriber);

    ActivityDelegate unsubscribe(ActivitySubscribe subscriber);

    <T extends ActivitySubscribe> T getSubscriber(Class<T> subscriber);

    <T extends ActivitySubscribe> T useSubscriber(Class<T> subscriber);

    <T extends ActivitySubscribe> T useSubscriber(T subscriber);

    IntentDelegate intent();
}

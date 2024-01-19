package com.linsh.base.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.linsh.base.LshLog;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   : 观察者 RecyclerView Adapter
 *
 *             用法可参考 ObservableActivity
 * </pre>
 */
public abstract class ObservableRcvAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IObservableRcvAdapter {

    private static final String TAG = "ObservableRcvAdapter";
    /**
     * 该 Map 包含了所有已经被订阅了的订阅者.
     * <p>
     * Map 中的 key 为订阅事件接口, value 为该事件的所有订阅者
     */
    private HashMap<Class, Set<RcvAdapterSubscribe>> mSubscribers;

    /**
     * 对于传入 Class 字节码类型的订阅者, 将只保留一个实例, 其他地方需要使用, 会对其进行复用
     * <p>
     * Map 中的 key 为订阅者 Class, value 为 Class 类型传入后自动实例化的所有订阅者
     */
    private HashMap<Class, RcvAdapterSubscribe> mSingleInstanceSubscribers;

    /**
     * 静态缓存已经使用了的订阅者所匹配的事件, 下次再使用时可省略查找订阅事件的步骤.
     * <p>
     * Map 中的 key 为订阅者的 Class, value 该订阅者所有的订阅事件接口列表
     */
    private static HashMap<Class, List<Class<? extends RcvAdapterSubscribe>>> sCachedSubscriberClasses = new HashMap<>();

    /**
     * 注册订阅者
     * <p>
     * 传入的类对象会被自动实例化, 且默认使用无参构造实例化. 如果实例化失败, 则会订阅失败
     *
     * @param classOfSubscriber 订阅者类对象
     * @return 是否成功订阅
     */
    @Override
    public <T extends RcvAdapterSubscribe> T subscribe(Class<T> classOfSubscriber) {
        if (mSingleInstanceSubscribers == null)
            mSingleInstanceSubscribers = new HashMap<>();
        // 检查如果已经订阅, 则返回该类的实例
        RcvAdapterSubscribe instance = mSingleInstanceSubscribers.get(classOfSubscriber);
        if (instance != null) {
            // 该订阅者已经自动创建过实例, 直接使用缓存实例
            return (T) instance;
        }
        // 没有订阅该类, 则创建实例, 对该实例进行订阅
        try {
            Constructor<T> constructor = classOfSubscriber.getDeclaredConstructor();
            constructor.setAccessible(true);
            T subscribe = constructor.newInstance();
            subscribe = subscribe(subscribe);
            mSingleInstanceSubscribers.put(classOfSubscriber, subscribe);
            return subscribe;
        } catch (Exception e) {
            throw new RuntimeException("实例化 " + classOfSubscriber.getName() + " 失败");
        }
    }

    /**
     * 注册订阅者
     *
     * @param subscriber 订阅者
     * @return 是否成功订阅
     */
    @Override
    public <T extends RcvAdapterSubscribe> T subscribe(T subscriber) {
        if (mSubscribers == null)
            mSubscribers = new HashMap<>();

        List<Class<? extends RcvAdapterSubscribe>> cache = sCachedSubscriberClasses.get(subscriber.getClass());
        if (cache != null) {
            // 该类的订阅事件接口已经缓存过, 不需要再重新查找
            if (cache.size() > 0) {
                for (Class<? extends RcvAdapterSubscribe> subscribeAPI : cache) {
                    Set<RcvAdapterSubscribe> list = mSubscribers.get(subscribeAPI);
                    if (list == null) {
                        list = new LinkedHashSet<>();
                        mSubscribers.put(subscribeAPI, list);
                    }
                    list.add(subscriber);
                }
            } else {
                LshLog.e(TAG, "find empty subscribeAPI cache list, please check");
            }
            // 如果重复对同一个实例进行订阅, 每次订阅都会调用一次 attach() 方法
            subscriber.attach(this);
            return subscriber;
        }
        // 没有该订阅者的订阅事件接口缓存, 开始查找并缓存
        Class clazz = subscriber.getClass();
        cache = new LinkedList<>();
        while (clazz != null) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> callbackClass : interfaces) {
                if (RcvAdapterSubscribe.class.isAssignableFrom(callbackClass) &&
                        RcvAdapterSubscribe.Register.contains((Class<? extends RcvAdapterSubscribe>) callbackClass)) {
                    Set<RcvAdapterSubscribe> list = mSubscribers.get(callbackClass);
                    if (list == null) {
                        list = new LinkedHashSet<>();
                        mSubscribers.put(callbackClass, list);
                    }
                    list.add(subscriber);
                    cache.add((Class<? extends RcvAdapterSubscribe>) callbackClass);
                }
            }
            if (cache.size() > 0 && !RcvAdapterSubscribe.class.isAssignableFrom(clazz))
                break;
            clazz = clazz.getSuperclass();
        }
        sCachedSubscriberClasses.put(subscriber.getClass(), cache);
        if (cache.size() == 0) {
            LshLog.e(TAG, "make empty subscribeAPI cache list, please check");
        }
        subscriber.attach(this);
        return subscriber;
    }

    /**
     * 判断是否已经订阅该事件的类
     *
     * @param classOfSubscriber 订阅者实现类
     * @return 如果订阅者中存在该类的实例, 则返回 true, 否则为 false
     */
    @Override
    public boolean isSubscribed(Class<? extends RcvAdapterSubscribe> classOfSubscriber) {
        return mSingleInstanceSubscribers != null && mSingleInstanceSubscribers.containsKey(classOfSubscriber);
    }

    /**
     * 判断是否已经订阅该事件的实例
     *
     * @param subscriber 订阅者实例
     * @return 如果订阅者中存在该实例, 则返回 true, 否则为 false
     */
    @Override
    public boolean isSubscribed(RcvAdapterSubscribe subscriber) {
        if (subscriber == null) return false;
        // 找到一个该订阅者的订阅事件接口
        List<Class<? extends RcvAdapterSubscribe>> cache = sCachedSubscriberClasses.get(subscriber.getClass());
        if (cache == null || cache.size() == 0) return false;
        // 根据该接口去查找其中一处肯定存在该类的订阅事件接口的实现者缓存, 并判断是否存在该类
        Set<RcvAdapterSubscribe> subscribeSet = mSubscribers.get(cache.iterator().next());
        if (subscribeSet == null) return false;
        return subscribeSet.contains(subscriber);
    }

    /**
     * 解注册该类订阅者
     * <p>
     * 注意: 如果该类存在多个实例的订阅者, 会将全部的订阅者进行解注册
     *
     * @param classOfSubscriber 订阅者类对象
     */
    @Override
    public boolean unsubscribe(Class<? extends RcvAdapterSubscribe> classOfSubscriber) {
        return mSingleInstanceSubscribers != null && mSingleInstanceSubscribers.remove(classOfSubscriber) != null;
    }

    /**
     * 解注册订阅者
     *
     * @param subscriber 订阅者
     */
    @Override
    public boolean unsubscribe(RcvAdapterSubscribe subscriber) {
        if (subscriber == null) return false;
        List<Class<? extends RcvAdapterSubscribe>> cache = sCachedSubscriberClasses.get(subscriber.getClass());
        if (cache == null || cache.size() == 0) return false;
        boolean res = false;
        for (Class<? extends RcvAdapterSubscribe> subscribeAPI : cache) {
            Set<RcvAdapterSubscribe> subscribers = mSubscribers.get(subscribeAPI);
            if (subscribers != null) {
                subscribers.remove(subscriber);
                res = true;
            }
        }
        return res;
    }

    //========================================= Implement =========================================//
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (mSubscribers != null) {
            Set<RcvAdapterSubscribe> callbacks = mSubscribers.get(RcvAdapterSubscribe.OnBindViewHolder.class);
            if (callbacks != null) {
                for (RcvAdapterSubscribe callback : callbacks) {
                    ((RcvAdapterSubscribe.OnBindViewHolder) callback).onBindViewHolder(holder, position);
                }
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mSubscribers != null) {
            Set<RcvAdapterSubscribe> callbacks = mSubscribers.get(RcvAdapterSubscribe.OnAttachedToRecyclerView.class);
            if (callbacks != null) {
                for (RcvAdapterSubscribe callback : callbacks) {
                    ((RcvAdapterSubscribe.OnAttachedToRecyclerView) callback).onAttachedToRecyclerView(recyclerView);
                }
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mSubscribers != null) {
            Set<RcvAdapterSubscribe> callbacks = mSubscribers.get(RcvAdapterSubscribe.OnDetachedFromRecyclerView.class);
            if (callbacks != null) {
                for (RcvAdapterSubscribe callback : callbacks) {
                    ((RcvAdapterSubscribe.OnDetachedFromRecyclerView) callback).onDetachedFromRecyclerView(recyclerView);
                }
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
        if (mSubscribers != null) {
            Set<RcvAdapterSubscribe> callbacks = mSubscribers.get(RcvAdapterSubscribe.OnViewAttachedToWindow.class);
            if (callbacks != null) {
                for (RcvAdapterSubscribe callback : callbacks) {
                    ((RcvAdapterSubscribe.OnViewAttachedToWindow) callback).onViewAttachedToWindow(holder);
                }
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        super.onViewDetachedFromWindow(holder);
        if (mSubscribers != null) {
            Set<RcvAdapterSubscribe> callbacks = mSubscribers.get(RcvAdapterSubscribe.OnViewDetachedFromWindow.class);
            if (callbacks != null) {
                for (RcvAdapterSubscribe callback : callbacks) {
                    ((RcvAdapterSubscribe.OnViewDetachedFromWindow) callback).onViewDetachedFromWindow(holder);
                }
            }
        }
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        super.onViewRecycled(holder);
        if (mSubscribers != null) {
            Set<RcvAdapterSubscribe> callbacks = mSubscribers.get(RcvAdapterSubscribe.OnViewRecycled.class);
            if (callbacks != null) {
                for (RcvAdapterSubscribe callback : callbacks) {
                    ((RcvAdapterSubscribe.OnViewRecycled) callback).onViewRecycled(holder);
                }
            }
        }
    }
}

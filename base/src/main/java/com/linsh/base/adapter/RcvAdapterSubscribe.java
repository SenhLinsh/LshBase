package com.linsh.base.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   : RecyclerView.Adapter 订阅相关接口
 * </pre>
 */
public interface RcvAdapterSubscribe {

    /**
     * 绑定 Adapter
     */
    void attach(RecyclerView.Adapter<?> adapter);

    /**
     * 为 Adapter 回调相关的订阅事件接口进行注册, 只有注册过的订阅接口才能获得订阅回调的能力.
     * 擅自继承 RcvAdapterSubscribe 的接口由于无法进行注册, 所以无法获得订阅回调的能力.
     */
    class Register {

        /**
         * 已注册的订阅回调时间接口的集合
         */
        static final HashSet<Class<? extends RcvAdapterSubscribe>> DATA;

        static {
            DATA = new HashSet<>();
            DATA.add(OnBindViewHolder.class);
            DATA.add(OnAttachedToRecyclerView.class);
            DATA.add(OnDetachedFromRecyclerView.class);
            DATA.add(OnViewAttachedToWindow.class);
            DATA.add(OnViewDetachedFromWindow.class);
            DATA.add(OnViewRecycled.class);
        }

        public static void register(Class<? extends RcvAdapterSubscribe> clazz) {
            DATA.add(clazz);
        }

        public static boolean contains(Class<? extends RcvAdapterSubscribe> clazz) {
            return DATA.contains(clazz);
        }
    }

    interface OnBindViewHolder extends RcvAdapterSubscribe {
        void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
    }

    interface OnAttachedToRecyclerView extends RcvAdapterSubscribe {
        void onAttachedToRecyclerView(RecyclerView recyclerView);
    }

    interface OnDetachedFromRecyclerView extends RcvAdapterSubscribe {
        void onDetachedFromRecyclerView(RecyclerView recyclerView);
    }

    interface OnViewAttachedToWindow extends RcvAdapterSubscribe {
        void onViewAttachedToWindow(RecyclerView.ViewHolder holder);
    }

    interface OnViewDetachedFromWindow extends RcvAdapterSubscribe {
        void onViewDetachedFromWindow(RecyclerView.ViewHolder holder);
    }

    interface OnViewRecycled extends RcvAdapterSubscribe {
        void onViewRecycled(RecyclerView.ViewHolder holder);
    }
}

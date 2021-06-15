package com.linsh.base.mvp;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   : MVP 基础接口
 * </pre>
 */
public interface Contract {

    interface View {

        void attachPresenter(Contract.Presenter presenter);

        void detachPresenter();

        /**
         * 获取 Context
         *
         * @return {@link BaseMvpActivity} 基类已经实现该接口, 不建议重写, 默认返回组件 Context, 解绑后返回 App Context
         */
        @NonNull
        Context getContext();
    }

    interface Presenter {
        void attachView(Contract.View view);

        void detachView();
    }
}

package com.linsh.base.mvp;

import android.content.Context;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   :
 * </pre>
 */
public interface Contract {

    interface Presenter {
        void attachView(Contract.View view);

        void detachView();
    }

    interface View {

        void attachPresenter(Contract.Presenter presenter);

        void detachPresenter();

        Context getContext();
    }
}

package com.linsh.base.activity.mvp;

import com.linsh.base.activity.Contract;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   :
 * </pre>
 */
public abstract class PresenterImpl<V extends Contract.View> implements Contract.Presenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;
        attachView();
    }

    protected abstract void attachView();

    @Override
    public void detachView() {
    }

    public V getView() {
        return mView;
    }
}

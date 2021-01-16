package com.linsh.base.mvp;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   :
 * </pre>
 */
public abstract class PresenterImpl<V extends Contract.View> implements Contract.Presenter {

    private V mView;

    @Override
    public void attachView(Contract.View view) {
        mView = (V) view;
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

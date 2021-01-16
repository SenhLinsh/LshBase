package com.linsh.base.mvp;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/02/25
 *    desc   :
 * </pre>
 */
public abstract class BaseMvpViewImpl<P extends Contract.Presenter> implements Contract.View {

    private P delegatePresenter;

    @Override
    public void attachPresenter(Contract.Presenter presenter) {
        delegatePresenter = (P) presenter;
    }

    @Override
    public void detachPresenter() {
    }

    protected P getPresenter() {
        return delegatePresenter;
    }
}

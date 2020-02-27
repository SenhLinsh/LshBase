package com.linsh.base.mvp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/01/05
 *    desc   :
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MinorPresenter {

    /**
     * @return 以数组形式返回辅助Presenter, 且需要 Presenter 接口和实例的 Class 成对出现. 如: @MinorPresenter({AContract.Presenter.class, APresenterImpl.class})
     */
    Class<? extends Contract.Presenter>[] value();
}

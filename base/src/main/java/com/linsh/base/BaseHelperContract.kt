package com.linsh.base

import com.linsh.base.helper.interf.ActivityHelperInterface

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/08
 *    desc   :
 * </pre>
 */
interface BaseHelperContract {

    interface View<out P : Presenter<View<P>>> : BaseContract.View<P> {

        fun addHelper(helper: ActivityHelperInterface)

        fun removeHelper(helper: ActivityHelperInterface)
    }

    interface Presenter<out V : View<Presenter<V>>> : BaseContract.Presenter<V> {

    }
}
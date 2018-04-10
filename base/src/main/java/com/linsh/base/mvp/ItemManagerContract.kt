package com.linsh.base.mvp

import android.support.v7.widget.RecyclerView
import com.linsh.base.BaseHelperContract

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/08
 *    desc   : 用于管理条目的 MVP 接口
 * </pre>
 */
interface ItemManagerContract {

    // TODO 未完成
    interface View<out P : Presenter<View<P>>> : BaseHelperContract.View<P> {

        fun setTitle(title: String)
    }

    // TODO  未完成
    interface Presenter<out V : View<Presenter<V>>> : BaseHelperContract.Presenter<V> {

        fun getAdapter(): RecyclerView.Adapter<*>
    }
}
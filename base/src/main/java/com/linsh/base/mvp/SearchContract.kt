package com.linsh.base.mvp

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import com.linsh.base.BaseHelperContract

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/08
 *    desc   : 用于查询的 MVP 接口
 * </pre>
 */
interface SearchContract {

    interface View<out P : Presenter<View<P>>> : BaseHelperContract.View<P> {

        fun setTitle(title: String)
    }

    interface Presenter<out V : View<Presenter<V>>> : BaseHelperContract.Presenter<V> {

        fun getOnQueryTextListener(): SearchView.OnQueryTextListener

        fun getAdapter(): RecyclerView.Adapter<*>
    }
}
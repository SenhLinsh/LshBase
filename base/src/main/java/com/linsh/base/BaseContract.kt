package com.linsh.base

import android.app.Activity
import android.content.Context

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/08
 *    desc   :
 * </pre>
 */
interface BaseContract {

    interface View<out P : Presenter<View<P>>> {

        val presenter: P

        val context: Context

        val activity: Activity

        fun showToast(content: String)

        fun showToastLong(content: String)

        fun finishActivity()

        fun finishActivity(resultCode: Int)
    }

    interface Presenter<out V : View<Presenter<V>>> {

        val view: V

        fun attachView()

        fun detachView()

        fun subscribe()

        fun unsubscribe()
    }
}

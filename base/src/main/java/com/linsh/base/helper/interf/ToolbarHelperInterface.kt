package com.linsh.base.helper.interf

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import com.linsh.base.helper.interf.ActivityHelperInterface

/**
 * <pre>
 * author : Senh Linsh
 * github : https://github.com/SenhLinsh
 * date   : 2018/03/29
 * desc   :
 * </pre>
 */
interface ToolbarHelperInterface : ActivityHelperInterface {

    fun setTitle(title: CharSequence)

    fun getTitle(): String

    fun setLogo(resId: Int)

    fun setLogo(drawable: Drawable?)

    fun setNavigationIcon(resId: Int)

    fun setNavigationIcon(drawable: Drawable?)

    fun setBackgroundColor(color: Int)

    fun setContentView(activity: Activity, layoutRes: Int)

    fun setContentView(activity: Activity, view: View)
}

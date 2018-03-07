package com.linsh.base.widget

import android.app.Dialog
import android.content.DialogInterface
import android.view.View

/**
 * <pre>
 * author : Senh Linsh
 * date   : 2018/02/10
 * desc   : Dialog 帮助类的接口, 方便一句话弹出所需窗口
 * </pre>
 */
interface IDialogHelper {

    fun showTextDialog(title: String, content: String,
                       positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showTextDialog(content: String,
                       positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog
//
//    fun showInputDialog(title: String, content: String? = null, hint: String? = null,
//                        positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showListDialog(title: String, items: Array<String>, listener: DialogInterface.OnClickListener? = null,
                       positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showListDialog(title: String, items: List<String>, listener: DialogInterface.OnClickListener? = null,
                       positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showListDialog(items: Array<String>, listener: DialogInterface.OnClickListener? = null,
                       positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showListDialog(items: List<String>, listener: DialogInterface.OnClickListener? = null,
                       positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showSingleChoiceListDialog(title: String, items: Array<CharSequence>, listener: DialogInterface.OnClickListener? = null,
                                   positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showSingleChoiceListDialog(title: String, items: Array<CharSequence>, checkedItem: Int, listener: DialogInterface.OnClickListener? = null,
                                   positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showSingleChoiceListDialog(items: Array<CharSequence>, listener: DialogInterface.OnClickListener? = null,
                                   positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showSingleChoiceListDialog(items: Array<CharSequence>, checkedItem: Int, listener: DialogInterface.OnClickListener? = null,
                                   positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog


    fun showMultiChoiceListDialog(title: String, items: Array<CharSequence>, listener: DialogInterface.OnMultiChoiceClickListener? = null,
                                  positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showMultiChoiceListDialog(title: String, items: Array<CharSequence>, checkedItems: BooleanArray, listener: DialogInterface.OnMultiChoiceClickListener? = null,
                                  positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showMultiChoiceListDialog(items: Array<CharSequence>, listener: DialogInterface.OnMultiChoiceClickListener? = null,
                                  positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showMultiChoiceListDialog(items: Array<CharSequence>, checkedItems: BooleanArray, listener: DialogInterface.OnMultiChoiceClickListener? = null,
                                  positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showCustomDialog(title: String, view: View,
                         positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showCustomDialog(view: View,
                         positiveBtn: String? = null, onPositiveListener: DialogInterface.OnClickListener? = null, negativeBtn: String? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showLoadingDialog(content: String = "正在加载...", cancelable: Boolean = false): Dialog

    fun showLoadingDialog(content: String = "正在加载...", cancelListener: DialogInterface.OnCancelListener): Dialog

    fun dismissDialog()
}

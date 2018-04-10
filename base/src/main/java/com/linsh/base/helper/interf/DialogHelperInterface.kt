package com.linsh.base.helper.interf

import android.app.Dialog
import android.view.View
import com.linsh.base.dialog.*

/**
 * <pre>
 * author : Senh Linsh
 * date   : 2018/02/10
 * desc   : Dialog 帮助类的接口, 方便一句话弹出所需窗口
 * </pre>
 */
interface DialogHelperInterface : ActivityHelperInterface {

    fun showTextDialog(title: CharSequence? = null, content: CharSequence,
                       positiveBtn: CharSequence? = null, onPositiveListener: DialogInterface.OnClickListener? = null,
                       negativeBtn: CharSequence? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showTextDialog(content: CharSequence,
                       positiveBtn: CharSequence? = null, onPositiveListener: DialogInterface.OnClickListener? = null,
                       negativeBtn: CharSequence? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showInputDialog(title: CharSequence?, content: CharSequence? = null, hint: CharSequence? = null,
                        positiveBtn: CharSequence? = null, onPositiveListener: InputDialogInterface.OnClickListener? = null,
                        negativeBtn: CharSequence? = null, onNegativeListener: InputDialogInterface.OnClickListener? = null): Dialog

    fun showListDialog(title: CharSequence? = null, items: Array<String>, listener: ListDialogInterface.OnItemClickListener? = null,
                       positiveBtn: CharSequence? = null, onPositiveListener: DialogInterface.OnClickListener? = null,
                       negativeBtn: CharSequence? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showListDialog(title: CharSequence? = null, items: List<String>, listener: ListDialogInterface.OnItemClickListener? = null,
                       positiveBtn: CharSequence? = null, onPositiveListener: DialogInterface.OnClickListener? = null,
                       negativeBtn: CharSequence? = null, onNegativeListener: DialogInterface.OnClickListener? = null): Dialog

    fun showSingleChoiceDialog(title: CharSequence?, items: Array<String>, selectedItem: Int = -1, listener: SingleChoiceDialogInterface.OnItemClickListener? = null,
                               positiveBtn: CharSequence? = null, onPositiveListener: SingleChoiceDialogInterface.OnClickListener? = null,
                               negativeBtn: CharSequence? = null, onNegativeListener: SingleChoiceDialogInterface.OnClickListener? = null): Dialog

    fun showSingleChoiceDialog(title: CharSequence?, items: List<String>, selectedItem: Int = -1, listener: SingleChoiceDialogInterface.OnItemClickListener? = null,
                               positiveBtn: CharSequence? = null, onPositiveListener: SingleChoiceDialogInterface.OnClickListener? = null,
                               negativeBtn: CharSequence? = null, onNegativeListener: SingleChoiceDialogInterface.OnClickListener? = null): Dialog

    fun showMultiChoiceDialog(title: CharSequence?, items: Array<String>, checkedItems: BooleanArray? = null, listener: MultiChoiceDialogInterface.OnItemClickListener? = null,
                              positiveBtn: CharSequence? = null, onPositiveListener: MultiChoiceDialogInterface.OnClickListener? = null,
                              negativeBtn: CharSequence? = null, onNegativeListener: MultiChoiceDialogInterface.OnClickListener? = null): Dialog

    fun showCustomDialog(title: CharSequence? = null, view: View,
                         positiveBtn: CharSequence? = null, onPositiveListener: CustomDialogInterface.OnClickListener? = null,
                         negativeBtn: CharSequence? = null, onNegativeListener: CustomDialogInterface.OnClickListener? = null): Dialog

    fun showCustomDialog(view: View,
                         positiveBtn: CharSequence? = null, onPositiveListener: CustomDialogInterface.OnClickListener? = null,
                         negativeBtn: CharSequence? = null, onNegativeListener: CustomDialogInterface.OnClickListener? = null): Dialog

    fun showLoadingDialog(cancelListener: DialogInterface.OnCancelListener? = null): Dialog

    fun showLoadingDialog(content: CharSequence? = "正在加载...", cancelable: Boolean = false): Dialog

    fun showLoadingDialog(content: CharSequence? = "正在加载...", cancelListener: DialogInterface.OnCancelListener?): Dialog

    fun dismissDialog()
}

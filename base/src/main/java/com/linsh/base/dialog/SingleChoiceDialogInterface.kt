package com.linsh.base.dialog

import android.content.DialogInterface

/**
 * <pre>
 * author : Senh Linsh
 * github : https://github.com/SenhLinsh
 * date   : 2018/03/08
 * desc   :
</pre> *
 */
interface SingleChoiceDialogInterface {

    interface OnClickListener {
        fun onClick(dialog: DialogInterface, data: Array<String>, selectedItem: Int)
    }

    interface OnItemClickListener {
        fun onClick(dialog: DialogInterface, data: Array<String>, item: Int, lastSelectedItem: Int)
    }
}

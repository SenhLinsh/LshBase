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
interface InputDialogInterface {

    interface OnClickListener {
        fun onClick(dialog: DialogInterface, input: String)
    }
}

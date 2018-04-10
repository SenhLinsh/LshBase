package com.linsh.base.dialog

import android.content.DialogInterface
import android.view.View

/**
 * <pre>
 * author : Senh Linsh
 * github : https://github.com/SenhLinsh
 * date   : 2018/03/08
 * desc   :
</pre> *
 */
interface CustomDialogInterface {

    interface OnClickListener {
        fun onClick(dialog: DialogInterface, customView: View)
    }
}

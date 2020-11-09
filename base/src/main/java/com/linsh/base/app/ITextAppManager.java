package com.linsh.base.app;

import android.app.Activity;
import android.content.Context;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   :
 * </pre>
 */
public interface ITextAppManager {

    String EXTRA_TEXT_EDIT_PATH = "path";
    String EXTRA_TEXT_EDIT_TEXT = "text";

    void launch();

    void launch(Context context);

    void gotoEditFile(String filePath);

    void gotoEditFile(String filePath, Context context);

    void gotoEditFile(String filePath, Activity activity, int requestCode);

    void gotoEditText(String text);

    void gotoEditText(String text, Context context);

    void gotoEditText(String text, Activity activity, int requestCode);
}

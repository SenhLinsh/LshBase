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
    String EXTRA_TEXT_EDIT_EDIT = "edit";

    void launch();

    void launch(Context context);

    void gotoEditFile(String filePath, boolean isEditMode);

    void gotoEditFile(String filePath, boolean isEditMode, Context context);

    void gotoEditFile(String filePath, boolean isEditMode, Activity activity, int requestCode);

    void gotoEditFile(String filePath, String text, boolean isEditMode);

    void gotoEditFile(String filePath, String text, boolean isEditMode, Context context);

    void gotoEditFile(String filePath, String text, boolean isEditMode, Activity activity, int requestCode);

    void gotoEditText(String text, boolean isEditMode);

    void gotoEditText(String text, boolean isEditMode, Context context);

    void gotoEditText(String text, boolean isEditMode, Activity activity, int requestCode);
}

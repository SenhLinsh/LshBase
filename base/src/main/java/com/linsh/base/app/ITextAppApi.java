package com.linsh.base.app;

import android.app.Activity;
import android.content.Context;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   : 字字珠玑应用接口
 * </pre>
 */
public interface ITextAppApi {

    String PACKAGE_NAME = "com.linsh.text";
    String APP_NAME = "字字珠玑";
    String APP_NAME_EN = "text";

    String PATH_GIT = "/sdcard/.linsh/text/";
    String PATH_GIT_APP = PATH_GIT + "Develop/linsh/app/";

    String ACTIVITY_NAME_TEXT_EDIT = "com.linsh.text.page.text.TextEditActivity";
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

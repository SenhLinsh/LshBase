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

    void start();

    void start(Context context);

    void startTextEdit(String filePath);

    void startTextEdit(String filePath, Context context);

    void startTextEditForResult(String filePath, Activity activity, int requestCode);
}

package com.linsh.base.log.impl;

import android.util.Log;

import com.linsh.base.log.ILogAdapter;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/05
 *    desc   :
 * </pre>
 */
class LogcatAdapter implements ILogAdapter {

    private int printToLogcatLevel;

    public LogcatAdapter(int printToLogcatLevel) {
        this.printToLogcatLevel = printToLogcatLevel;
    }

    @Override
    public void log(int priority, String tag, String message, Throwable throwable) {
        if (priority < printToLogcatLevel) return;
        // 给 tag 加上 LshLog
        String finalTag;
        if (tag == null) {
            finalTag = "LshLog";
        } else {
            finalTag = "LshLog:" + tag;
        }
        // 拼接 message 和 throwable
        String finalMessage;
        if (message == null) {
            if (throwable == null) {
                finalMessage = "null";
            } else {
                finalMessage = Log.getStackTraceString(throwable);
            }
        } else {
            if (throwable == null) {
                finalMessage = message;
            } else {
                finalMessage = message + "\n" + Log.getStackTraceString(throwable);
            }
        }
        Log.println(Logger.getLogPriority(priority), finalTag, finalMessage);
    }
}

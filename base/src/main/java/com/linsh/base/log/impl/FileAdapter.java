package com.linsh.base.log.impl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.linsh.base.BuildConfig;
import com.linsh.base.log.ILogAdapter;
import com.linsh.base.log.ILogFileManager;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/05
 *    desc   :
 * </pre>
 */
class FileAdapter implements ILogAdapter {

    private final Handler handler;
    private static final int MESSAGE_WRITE_LOGS = 1;
    private final int printToLogFileLevel;
    private final int printToErrorFileLevel;

    public FileAdapter(@NonNull HandlerThread fileThread, ILogFileManager logFileManager, int printToLogFileLevel, int printToErrorFileLevel) {
        this.printToLogFileLevel = printToLogFileLevel;
        this.printToErrorFileLevel = printToErrorFileLevel;
        this.handler = new WriteHandler(fileThread.getLooper(), logFileManager);
    }

    @Override
    public void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        boolean printToLogFile = priority >= printToLogFileLevel;
        boolean printToErrorFile = priority >= printToErrorFileLevel;
        if (printToLogFile || printToErrorFile) {
            handler.sendMessage(handler.obtainMessage(MESSAGE_WRITE_LOGS,
                    new LogInfo(System.currentTimeMillis(), priority, tag, message, throwable, printToLogFile, printToErrorFile)));
        }
    }

    static class WriteHandler extends Handler {

        private final SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.UK);
        private final ILogFileManager logFileManager;

        public WriteHandler(@NonNull Looper looper, ILogFileManager logFileManager) {
            super(looper);
            this.logFileManager = logFileManager;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj instanceof LogInfo) {
                LogInfo info = (LogInfo) msg.obj;
                String message = info.message == null ? info.throwable == null ? "null" : Log.getStackTraceString(info.throwable) :
                        info.throwable == null ? info.message : info.message + "\n" + Log.getStackTraceString(info.throwable);
                String log = logFormat.format(new Date(info.timestamp)) + ' '
                        + ContextUtils.getPackageName() + ' '
                        + Logger.getPriorityLabel(info.priority)
                        + (info.tag == null ? ' ' : '/' + info.tag) + ": "
                        + message + StringUtils.lineSeparator();

                if (info.printToLogFile) {
                    writeLog(logFileManager.getLogFile(info.timestamp), log);
                }
                if (info.printToErrorFile) {
                    writeLog(logFileManager.getLogErrorFile(info.timestamp), log);
                }
            }
        }

        private void writeLog(File file, String line) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                fileWriter.append(line);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) {
                        Log.e(BuildConfig.TAG, "[please_check] IOException occurred while writing log");
                    }
                }
            }
        }
    }

    static class LogInfo {

        public final long timestamp;
        public final int priority;
        public final String tag;
        public final String message;
        public final Throwable throwable;
        public final boolean printToLogFile;
        public final boolean printToErrorFile;

        public LogInfo(long timestamp, int priority, String tag, String message, Throwable throwable, boolean printToLogFile, boolean printToErrorFile) {
            this.timestamp = timestamp;
            this.priority = priority;
            this.tag = tag;
            this.message = message;
            this.throwable = throwable;
            this.printToLogFile = printToLogFile;
            this.printToErrorFile = printToErrorFile;
        }
    }
}

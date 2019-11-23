package com.linsh.base.log.impl;

import android.os.Environment;
import android.util.Log;

import com.linsh.base.BuildConfig;
import com.linsh.base.log.ILogFileManager;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.DateUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/17
 *    desc   :
 * </pre>
 */
class LogFileManager implements ILogFileManager {

    private static final String FILE_EXTENSION = ".log";
    private static final File LOGGER_DIR = new File(Environment.getExternalStorageDirectory(), "imprexion/logger");

    private final String packageName = ContextUtils.getPackageName();
    private final SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.UK);
    private final long maxFileSize;

    private long nextDayBeginTime;
    private File logDir;
    private File logFile;

    private File logErrorDir;
    private File logErrorTimestampDir;
    private String logErrorTimestampDirName;
    private File logErrorFile;

    private int fixCount;

    private FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith(packageName);
        }
    };

    LogFileManager(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    @Override
    public File getLogFile(long time) {
        File logDir = getLoggerTimestampDir(this.logDir, "log", time);
        if (this.logDir != logDir) {
            this.logDir = logDir;
            this.logFile = null;
        }
        logFile = getLogFile(logDir, this.logFile);
        return logFile;
    }

    @Override
    public File getLogErrorFile(long time) {
        File logErrorDir = getLoggerTimestampDir(this.logErrorDir, "log_error", time);
        if (this.logErrorDir != logErrorDir) {
            this.logErrorDir = logErrorDir;
            this.logErrorFile = null;
        }

        // 获取最大时间戳的文件夹
        String[] timestampDirs = logErrorDir.list();
        if (timestampDirs == null || timestampDirs.length == 0) {
            // log_error 下不存在 <timestamp> 文件夹, 则创建
            logErrorTimestampDir = new File(logErrorDir, String.valueOf(System.currentTimeMillis() / 1000));
            this.logErrorFile = null;
        } else {
            // log_error 下存在超过之前记录的 <timestamp> 文件夹数, 说明生成了新的文件夹, 需要去时间戳最大的
            String maxTimestampName = null;
            for (int i = 0; i < timestampDirs.length; i++) {
                String name = timestampDirs[i];
                char firstChar;
                // 校验文件夹名字
                if (name.length() == 10 && (firstChar = name.charAt(0)) > '0' && firstChar <= '9') {
                    if (maxTimestampName == null)
                        maxTimestampName = name;
                    else if (maxTimestampName.compareTo(name) < 0)
                        maxTimestampName = name;
                }
            }
            if (logErrorTimestampDirName == null
                    || !logErrorTimestampDirName.equals(maxTimestampName)) {
                logErrorTimestampDirName = maxTimestampName;
                if (logErrorTimestampDirName == null) {
                    logErrorTimestampDirName = String.valueOf(System.currentTimeMillis() / 1000);
                    Log.e(BuildConfig.TAG, "[please_check] logErrorDir does not have timestamp dir");
                }
                logErrorTimestampDir = new File(logErrorDir, logErrorTimestampDirName);
                this.logErrorFile = null;
            }
        }

        logErrorFile = getLogFile(logErrorTimestampDir, this.logErrorFile);
        return logErrorFile;
    }

    /**
     * 日志文件名: <packageName>@<yyyyMMddHHmmss>.log
     */
    private String getFilenameWithoutExt() {
        return packageName + '@' + fileFormat.format(new Date());
    }

    /**
     * 获取 log 或 log_error 文件夹
     * <p>
     * 由于这两个的父文件夹是由当天的时间戳生成的, 因此到 00:00 时, 需要生成新的父级文件夹
     */
    private File getLoggerTimestampDir(File oldDir, String dirName, long time) {
        if (time >= nextDayBeginTime || oldDir == null) {
            long todayBeginTime = DateUtils.toDayBegin(time) / 1000;
            nextDayBeginTime = todayBeginTime + 24 * 60 * 60;
            oldDir = new File(LOGGER_DIR, todayBeginTime + "/" + dirName);
        }
        return oldDir;
    }

    /**
     * 获取当前需要写入的日志文件
     * <p>
     * 日志文件目录有:
     * 1. logger/<timestamp>/log/
     * 2. logger/<timestamp>/log_error/<timestamp>/
     */
    private File getLogFile(File logDir, File oldFile) {
        if (oldFile == null) {
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            // 获取当前应用的日志文件
            String[] list = logDir.list(filter);
            if (list == null || list.length == 0) {
                return new File(logDir, getFilenameWithoutExt() + FILE_EXTENSION);
            }
            oldFile = new File(logDir, Collections.max(Arrays.asList(list)));
        }
        // 如果文件不为 null, 则判断缓存文件是否超出大小限制
        if (oldFile.length() >= maxFileSize) {
            String filename = getFilenameWithoutExt() + FILE_EXTENSION;
            // 解决: 极端情况下, 短时间内大量打印导致文件超出大小限制, 但文件名没有递增
            if (filename.equals(oldFile.getName())) {
                filename = getFilenameWithoutExt() + ++fixCount + FILE_EXTENSION;
            }
            return new File(logDir, filename);
        }
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        return oldFile;
    }
}

package com.linsh.base.net.nas2.impl;

import com.hierynomus.msfscc.fileinformation.FileAllInformation;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.linsh.base.net.nas2.INasFileInfo;
import com.linsh.lshutils.utils.FileUtilsEx;
import com.linsh.utilseverywhere.DateUtils;
import com.linsh.utilseverywhere.FileUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/13
 *    desc   :
 * </pre>
 */
public class SmbjFileInfo implements INasFileInfo {

    private final boolean isDirectory;
    private final String filename;
    private final long lastModified;
    private final long length;

    public SmbjFileInfo(FileIdBothDirectoryInformation information) {
        this.isDirectory = FileUtilsEx.isDirectory(information.getFileAttributes());
        this.filename = information.getFileName();
        this.lastModified = information.getLastWriteTime().toEpochMillis();
        this.length = information.getEndOfFile();
    }

    public SmbjFileInfo(FileAllInformation information) {
        this.isDirectory = FileUtilsEx.isDirectory(information.getBasicInformation().getFileAttributes());
        this.filename = information.getNameInformation();
        this.lastModified = information.getBasicInformation().getLastWriteTime().toEpochMillis();
        this.length = information.getStandardInformation().getEndOfFile();
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public String getFileName() {
        return filename;
    }

    @Override
    public long lastModified() {
        return lastModified;
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public String toString() {
        return "[" + filename + ", " + DateUtils.getDateTimeStr(lastModified) + ", "
                + (isDirectory ? "dir" : FileUtils.getFormattedFileSize(length)) + ']';
    }
}

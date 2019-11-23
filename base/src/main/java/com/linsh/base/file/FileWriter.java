package com.linsh.base.file;

import android.graphics.Bitmap;

import com.linsh.base.common.Consumer;
import com.linsh.base.common.Task;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/24
 *    desc   :
 * </pre>
 */
public interface FileWriter {

    BufferedWriter writer();

    void writer(Consumer<BufferedWriter> callable);

    FileWriter write(String content);

    FileWriter write(String content, boolean append);

    FileWriter write(List<String> contents);

    FileWriter write(List<String> contents, boolean append);

    FileWriter writeLine();

    FileWriter writeLines(int lines);

    FileWriter write(Bitmap bitmap);

    FileWriter write(byte[] bytes);

    FileWriter write(InputStream stream);

    FileTask task();

    interface FileTask extends Task<Boolean> {

        FileTask write(String content);

        FileTask write(String content, boolean append);

        FileTask write(List<String> contents);

        FileTask write(List<String> contents, boolean append);

        FileTask writeLine();

        FileTask writeLines(int lines);

        FileTask write(Bitmap bitmap);

        FileTask write(byte[] bytes);

        FileTask write(InputStream stream);
    }
}

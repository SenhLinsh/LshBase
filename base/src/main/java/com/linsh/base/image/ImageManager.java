package com.linsh.base.image;

import android.widget.ImageView;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/12
 *    desc   :
 * </pre>
 */
public interface ImageManager {

    void load(int res, ImageView view);

    void load(File file, ImageView view);

    void load(String url, ImageView view);

    void load(String url, ImageView view, int placeholder);

    void load(String url, ImageView view, int placeholder, int error);

    ImageLoader with(String url);

    ImageLoader with(int resId);

    ImageLoader with(File file);
}

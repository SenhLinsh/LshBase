package com.linsh.base.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
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
public interface ImageLoader {

    ImageLoader image(String url);

    ImageLoader image(int resId);

    ImageLoader image(File file);

    ImageLoader bind(Context context);

    ImageLoader placeholder(int resId);

    ImageLoader placeholder(Drawable drawable);

    ImageLoader error(int resId);

    ImageLoader error(Drawable drawable);

    ImageLoader fallback(int resId);

    ImageLoader fallback(Drawable drawable);

    ImageLoader resize(int with, int height);

    void load(ImageView imageView);

    void loadBg(View view);

    void preload();

    void download(File file);
}

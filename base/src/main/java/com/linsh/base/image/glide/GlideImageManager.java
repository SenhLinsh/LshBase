package com.linsh.base.image.glide;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.linsh.base.image.ImageLoader;
import com.linsh.base.image.ImageManager;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   : 放到沙发上的范德萨发
 * </pre>
 */
public class GlideImageManager implements ImageManager {

    @Override
    public void load(int res, ImageView view) {
        Glide.with(view)
                .load(res)
                .into(view);
    }

    @Override
    public void load(File file, ImageView view) {
        Glide.with(view)
                .load(file)
                .into(view);
    }

    @Override
    public void load(String url, ImageView view) {
        Glide.with(view)
                .load(url)
                .into(view);
    }

    @Override
    public void load(String url, ImageView view, int placeholder) {
        Glide.with(view)
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder))
                .into(view);
    }

    @Override
    public void load(String url, ImageView view, int placeholder, int error) {
        Glide.with(view)
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder).error(error))
                .into(view);
    }

    @Override
    public ImageLoader with(String url) {
        return new ImageLoaderImpl(url);
    }

    @Override
    public ImageLoader with(int resId) {
        return new ImageLoaderImpl(resId);
    }

    @Override
    public ImageLoader with(File file) {
        return new ImageLoaderImpl(file);
    }
}

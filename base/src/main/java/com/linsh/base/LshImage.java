package com.linsh.base;

import android.widget.ImageView;

import com.linsh.base.image.ImageLoader;
import com.linsh.base.image.ImageManager;
import com.linsh.base.image.glide.GlideImageManager;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/03/29
 *    desc   :
 * </pre>
 */
public class LshImage {

    private static class Holder {
        private static final ImageManager delegate = new GlideImageManager();
    }

    private LshImage() {
    }

    /**
     * 加载图片
     *
     * @param res 资源文件 id
     */
    public static void load(int res, ImageView view) {
        Holder.delegate.load(res, view);
    }

    /**
     * 加载图片
     *
     * @param file 图片文件
     */
    public static void load(File file, ImageView view) {
        Holder.delegate.load(file, view);
    }

    /**
     * 加载图片
     *
     * @param url 图片地址
     */
    public static void load(String url, ImageView view) {
        Holder.delegate.load(url, view);
    }

    /**
     * 加载图片
     *
     * @param url         图片地址
     * @param placeholder 加载过程中的图片显示
     */
    public static void load(String url, ImageView view, int placeholder) {
        Holder.delegate.load(url, view, placeholder);
    }

    /**
     * 加载图片
     *
     * @param url         图片地址
     * @param placeholder 加载过程中的图片显示
     * @param error       加载失败后的图片显示
     */
    public static void load(String url, ImageView view, int placeholder, int error) {
        Holder.delegate.load(url, view, placeholder, error);
    }

    /**
     * 使用 ImageLoader 来配置加载选项
     *
     * @param url 图片地址
     */
    public static ImageLoader with(String url) {
        return Holder.delegate.with(url);
    }

    /**
     * 使用 ImageLoader 来配置加载选项
     *
     * @param resId 资源文件 id
     */
    public static ImageLoader with(int resId) {
        return Holder.delegate.with(resId);
    }

    /**
     * 使用 ImageLoader 来配置加载选项
     *
     * @param file 图片文件
     */
    public static ImageLoader with(File file) {
        return Holder.delegate.with(file);
    }
}

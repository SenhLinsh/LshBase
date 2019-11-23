package com.linsh.base.image.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.linsh.base.LshThread;
import com.linsh.base.image.ImageLoader;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.FileUtils;

import java.io.File;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
class ImageLoaderImpl implements ImageLoader {

    private Object image;
    private RequestOptions requestOptions;
    private Context context;

    public ImageLoaderImpl(String url) {
        image = url;
    }

    public ImageLoaderImpl(int resId) {
        image = resId;
    }

    public ImageLoaderImpl(File file) {
        image = file;
    }

    @Override
    public ImageLoader image(String url) {
        image = url;
        return this;
    }

    @Override
    public ImageLoader image(int resId) {
        image = resId;
        return this;
    }

    @Override
    public ImageLoader image(File file) {
        image = file;
        return this;
    }

    @Override
    public ImageLoader bind(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public ImageLoader placeholder(int resId) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.placeholder(resId);
        return this;
    }

    @Override
    public ImageLoader placeholder(Drawable drawable) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.placeholder(drawable);
        return this;
    }

    @Override
    public ImageLoader error(int resId) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.error(resId);
        return this;
    }

    @Override
    public ImageLoader error(Drawable drawable) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.error(drawable);
        return this;
    }

    @Override
    public ImageLoader fallback(int resId) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.fallback(resId);
        return this;
    }

    @Override
    public ImageLoader fallback(Drawable drawable) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.fallback(drawable);
        return this;
    }

    @Override
    public ImageLoader resize(int with, int height) {
        if (requestOptions == null)
            requestOptions = new RequestOptions();
        requestOptions = requestOptions.override(with, height);
        return this;
    }

    @Override
    public void load(ImageView imageView) {
        getRequestBuilder(Glide.with(imageView)).into(imageView);
    }

    @Override
    public void loadBg(View view) {
        getRequestBuilder(Glide.with(view)).into(new ViewTarget<View, Drawable>(view) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getView().setBackground(resource);
                }
            }
        });
    }

    @Override
    public void preload() {
        getRequestBuilder(Glide.with(context != null ? context : ContextUtils.get())).preload();
    }

    @Override
    public void download(final File file) {
        if (image instanceof String) {
            LshThread.io(new Runnable() {
                @Override
                public void run() {
                    try {
                        File src = Glide.with(ContextUtils.get())
                                .downloadOnly()
                                .load((String) image)
                                .submit()
                                .get();
                        FileUtils.copy(src, file);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            throw new IllegalArgumentException("必须使用 url 作为图片源");
        }
    }

    @NonNull
    private RequestBuilder<Drawable> getRequestBuilder(RequestManager manager) {
        RequestBuilder<Drawable> builder;
        if (image instanceof String) {
            builder = manager.load((String) image);
        } else if (image instanceof Integer) {
            builder = manager.load((Integer) image);
        } else if (image instanceof File) {
            builder = manager.load((File) image);
        } else {
            throw new IllegalArgumentException("必须设置 image");
        }
        if (requestOptions != null) {
            builder = builder.apply(requestOptions);
        }
        return builder;
    }
}

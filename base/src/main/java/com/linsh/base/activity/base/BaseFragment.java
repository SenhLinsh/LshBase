package com.linsh.base.activity.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/03/06
 *    desc   :
 * </pre>
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 通过 Layout id 自动设置 Content VIew
     */
    protected int onCreateViewByLayoutId() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = onCreateViewByLayoutId();
        if (layoutId > 0) {
            return inflater.inflate(layoutId, container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

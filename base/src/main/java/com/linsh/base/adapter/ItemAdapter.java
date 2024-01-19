package com.linsh.base.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 适配器
 *
 * @param <D> 数据类型
 * @param <T> item类型
 */
public interface ItemAdapter<D, T> {

    /**
     * 获取item数据
     *
     * @param data     源数据
     * @param position 位置
     */
    T get(@NonNull D data, int position);

    /**
     * 获取item数量
     *
     * @param data 源数据
     * @return item数量
     */
    int size(@Nullable D data);

    //========================================= 默认适配器 =========================================//
    ItemAdapter<List<?>, Object> LIST_ADAPTER = new ItemAdapter<List<?>, Object>() {
        @Override
        public Object get(@NonNull List<?> data, int position) {
            return data.get(position);
        }

        @Override
        public int size(@Nullable List<?> data) {
            return data == null ? 0 : data.size();
        }
    };
    ItemAdapter<Object[], Object> ARRAY_ADAPTER = new ItemAdapter<Object[], Object>() {
        @Override
        public Object get(@NonNull Object[] data, int position) {
            return data[position];
        }

        @Override
        public int size(@Nullable Object[] data) {
            return data == null ? 0 : data.length;
        }
    };
}
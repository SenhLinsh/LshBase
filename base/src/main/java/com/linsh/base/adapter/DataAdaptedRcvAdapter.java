package com.linsh.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.linsh.utilseverywhere.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/12/27
 *    desc   : 数据自适应的 RecyclerView 适配器
 *
 *             可以注册多种类型的 ViewHolder, 并根据数据自动选择使用哪种 ViewHolder
 * </pre>
 */
public class DataAdaptedRcvAdapter extends BaseRcvAdapter<DataAdaptedRcvAdapter.DataAdaptedViewHolder<?>> {

    private static final String TAG = "DataAdaptedRcvAdapter";
    // ViewHolder 类型列表
    private final List<HolderBinder> mViewHolderBinders = new ArrayList<>();
    // 默认的 ViewHolder 类型
    private HolderBinder mDefaultViewHolderBinder;
    // 数据
    private Object data;
    // item适配器
    private ItemAdapter itemAdapter;
    // 点击事件监听器
    private OnItemClickListener mOnItemClickListener;
    // 长按事件监听器
    private OnItemLongClickListener mOnItemLongClickListener;

    public DataAdaptedRcvAdapter() {
    }

    public DataAdaptedRcvAdapter(Class<? extends DataAdaptedViewHolder> viewHolderClass) {
        registerViewHolder(new DataAdaptedHolderFactory(viewHolderClass), null);
    }

    /**
     * 设置数据
     *
     * @param data        数据
     * @param itemAdapter item适配器
     */
    public <T> void setData(@Nullable T data, @NonNull ItemAdapter<T, ?> itemAdapter) {
        this.data = data;
        this.itemAdapter = itemAdapter;
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     *
     * @param list 数据列表
     */
    public void setData(@Nullable List<?> list) {
        this.data = list;
        this.itemAdapter = ItemAdapter.LIST_ADAPTER;
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     *
     * @param array 数据列表
     */
    public void setData(@Nullable Object[] array) {
        this.data = array;
        this.itemAdapter = ItemAdapter.ARRAY_ADAPTER;
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     *
     * @return 数据列表
     */
    public Object getData() {
        return data;
    }

    /**
     * 通过 layout id 注册 ViewHolder
     *
     * @param layoutId   布局 id
     * @param itemBinder item 绑定器
     */
    public <T> HolderBinder registerViewHolder(int layoutId, ItemBinder<T> itemBinder) {
        return registerViewHolder(new ResIdHolderFactory<T>(layoutId, itemBinder), null);
    }

    /**
     * 通过 layout id 注册 ViewHolder
     *
     * @param layoutId   布局 id
     * @param itemBinder item 绑定器
     * @param listener   ViewHolder 事件监听器
     */
    public <T> HolderBinder registerViewHolder(int layoutId, ItemBinder<T> itemBinder, HolderEventListener<T> listener) {
        return registerViewHolder(new ResIdHolderFactory<T>(layoutId, itemBinder), listener);
    }

    /**
     * 注册 ViewHolder
     * <p>
     * 注：ViewHolder 子类实现时，必须仅保留一个构造方法，且参数必须为 ViewGroup，用于 Adapter 反射实例化 ViewHolder
     *
     * @param viewHolderClass ViewHolder 类
     */
    public <T> HolderBinder registerViewHolder(Class<? extends DataAdaptedViewHolder<? extends T>> viewHolderClass) {
        return registerViewHolder(new DataAdaptedHolderFactory(viewHolderClass), null);
    }

    /**
     * 注册 ViewHolder
     * <p>
     * 注：ViewHolder 子类实现时，必须仅保留一个构造方法，且参数必须为 ViewGroup，用于 Adapter 反射实例化 ViewHolder
     *
     * @param viewHolderClass ViewHolder 类
     * @param listener        ViewHolder 事件监听器
     */
    public <T> HolderBinder registerViewHolder(Class<? extends DataAdaptedViewHolder<? extends T>> viewHolderClass, HolderEventListener<T> listener) {
        return registerViewHolder(new DataAdaptedHolderFactory(viewHolderClass), listener);
    }

    /**
     * 注册 ViewHolder
     * <p>
     * 注：ViewHolder 子类实现时，必须仅保留一个构造方法，且参数必须为 ViewGroup，用于 Adapter 反射实例化 ViewHolder
     *
     * @param dataType        数据类型
     * @param viewHolderClass ViewHolder 类
     */
    public <T> HolderBinder registerViewHolder(Class<T> dataType, Class<? extends DataAdaptedViewHolder<? extends T>> viewHolderClass) {
        return registerViewHolder(new DataAdaptedHolderFactory(dataType, viewHolderClass), null);
    }

    /**
     * 注册 ViewHolder
     * <p>
     * 注：ViewHolder 子类实现时，必须仅保留一个构造方法，且参数必须为 ViewGroup，用于 Adapter 反射实例化 ViewHolder
     *
     * @param dataType        数据类型
     * @param viewHolderClass ViewHolder 类
     * @param listener        ViewHolder 事件监听器
     */
    public <T> HolderBinder registerViewHolder(Class<T> dataType, Class<? extends DataAdaptedViewHolder<? extends T>> viewHolderClass, HolderEventListener<T> listener) {
        return registerViewHolder(new DataAdaptedHolderFactory(dataType, viewHolderClass), listener);
    }

    /**
     * 注册 ViewHolder
     *
     * @param holderFactory ViewHolder 工厂
     */
    public <T> HolderBinder registerViewHolder(@NonNull HolderFactory holderFactory) {
        return registerViewHolder(holderFactory, null);
    }

    /**
     * 注册 ViewHolder
     *
     * @param holderFactory ViewHolder 工厂
     * @param listener      ViewHolder 事件监听器
     */
    public <T> HolderBinder registerViewHolder(@NonNull HolderFactory holderFactory, @Nullable HolderEventListener<T> listener) {
        HolderBinder binder = new HolderBinder(holderFactory, listener);
        mViewHolderBinders.add(binder);
        return binder;
    }

    /**
     * 设置默认的 viewHolderClass
     */
    public HolderBinder setDefaultViewHolder(Class<? extends DataAdaptedViewHolder<Object>> viewHolderClass) {
        return setDefaultViewHolder(viewHolderClass, null);
    }

    /**
     * 设置默认的 viewHolderClass
     */
    public HolderBinder setDefaultViewHolder(Class<? extends DataAdaptedViewHolder<Object>> viewHolderClass, HolderEventListener<Object> listener) {
        return mDefaultViewHolderBinder = new HolderBinder(new DataAdaptedHolderFactory(Object.class, viewHolderClass), listener);
    }

    /**
     * 设置点击事件监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 设置长按事件监听器
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return itemAdapter == null ? 0 : itemAdapter.size(data);
    }

    @Override
    public int getItemViewType(int position) {
        if (data != null) {
            // 遍历 holderFactory，找到可以处理 item 的 holderFactory
            Object item = itemAdapter.get(this.data, position);
            for (int i = 0; i < mViewHolderBinders.size(); i++) {
                HolderBinder holderBinder = mViewHolderBinders.get(i);
                if (holderBinder.holderFactory.handle(item, position)) {
                    return i;
                }
            }
            if (mDefaultViewHolderBinder != null) {
                return -1;
            }
            if (mViewHolderBinders.size() == 0) {
                throw new RuntimeException("请先注册 ViewHolder");
            }
            throw new RuntimeException("未注册 ViewHolder 的数据类型: " + data.getClass().getName());
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public DataAdaptedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == -1) {
            return mDefaultViewHolderBinder.holderFactory.create(parent);
        }
        return mViewHolderBinders.get(viewType).holderFactory.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdaptedViewHolder holder, int position) {
        holder.onBindItem(itemAdapter.get(data, position), position);
    }

    public static abstract class DataAdaptedViewHolder<T> extends RecyclerView.ViewHolder {

        private HolderEventListener<T> eventListener;

        /**
         * 通过布局 id 构造 ViewHolder
         * <p>
         * 注：子类实现时，必须仅保留一个构造方法，且参数必须为 ViewGroup，用于 Adapter 反射实例化 ViewHolder
         *
         * @param parent   父布局
         * @param layoutId 子布局 id
         */
        public DataAdaptedViewHolder(@NonNull ViewGroup parent, int layoutId) {
            this(parent, LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        }

        /**
         * 通过布局实例构造 ViewHolder
         * <p>
         * 注：子类实现时，必须仅保留一个构造方法，且参数必须为 ViewGroup，用于 Adapter 反射实例化 ViewHolder
         *
         * @param parent   父布局
         * @param itemView 子布局
         */
        public DataAdaptedViewHolder(@Nullable ViewGroup parent, View itemView) {
            super(itemView);
            // 设置点击事件
            itemView.setOnClickListener(v -> {
                DataAdaptedRcvAdapter adapter = getMyAdapter();
                if (adapter.mOnItemClickListener != null) {
                    int position = getAbsoluteAdapterPosition();
                    adapter.mOnItemClickListener.onItemClick(this,
                            adapter.itemAdapter.get(adapter.data, position), position);
                }
            });
            // 设置长按事件
            itemView.setOnLongClickListener(v -> {
                DataAdaptedRcvAdapter adapter = getMyAdapter();
                if (adapter.mOnItemLongClickListener != null) {
                    int position = getAbsoluteAdapterPosition();
                    adapter.mOnItemLongClickListener.onItemLongClick(this,
                            adapter.itemAdapter.get(adapter.data, position), position);
                }
                return true;
            });
        }

        /**
         * 绑定条目数据
         *
         * @param item     条目数据
         * @param position 位置
         */
        public abstract void onBindItem(@NonNull T item, int position);

        /**
         * 获取 Adapter
         *
         * @return Adapter
         */
        protected DataAdaptedRcvAdapter getMyAdapter() {
            return (DataAdaptedRcvAdapter) getBindingAdapter();
        }

        /**
         * 获取事件监听器
         *
         * @return 事件监听器
         */
        @Nullable
        protected HolderEventListener<T> getEventListener() {
            return eventListener;
        }
    }

    /**
     * ViewHolder 绑定器
     * <p>
     * 用于绑定 ViewHolder 类型和数据类型及事件监听器
     */
    public static class HolderBinder {
        private final HolderFactory holderFactory;
        private HolderEventListener holderEventListener;

        public HolderBinder(HolderFactory factory, HolderEventListener holderEventListener) {
            this.holderFactory = factory;
            this.holderEventListener = holderEventListener;
        }

        public void setHolderEventListener(HolderEventListener holderEventListener) {
            this.holderEventListener = holderEventListener;
        }
    }

    /**
     * 条目绑定器
     * <p>
     * 用于 DataAdaptedRcvAdapter 绑定条目数据的扩展接口
     */
    public interface ItemBinder<T> {

        /**
         * 绑定条目数据
         *
         * @param item     条目数据
         * @param position 位置
         */
        void onBindItem(RecyclerView.ViewHolder holder, @NonNull T item, int position);
    }

    /**
     * 默认 ViewHolder 绑定器
     * <p>
     * 用于绑定默认 ViewHolder 类型和数据类型及事件监听器
     */
    public interface HolderFactory {
        /**
         * 判断数据类型是否匹配
         *
         * @param item     数据
         * @param position 位置
         * @return 是否匹配
         */
        boolean handle(Object item, int position);

        /**
         * 创建 ViewHolder
         *
         * @param parent 父布局
         * @return ViewHolder
         */
        DataAdaptedViewHolder<?> create(ViewGroup parent);
    }

    /**
     * 默认 ViewHolder 绑定器
     * <p>
     * 用于绑定默认 ViewHolder 类型和数据类型及事件监听器
     */
    public static class DataAdaptedHolderFactory implements HolderFactory {
        private final Class<?> itemType;
        private final Class<? extends DataAdaptedViewHolder> viewHolderClass;

        public DataAdaptedHolderFactory(Class<? extends DataAdaptedViewHolder> viewHolderClass) {
            Type genericType = ClassUtils.getGenericType(viewHolderClass, DataAdaptedViewHolder.class, 0);
            if (genericType == null)
                throw new RuntimeException("请指定 ViewHolder 的泛型");
            if (!(genericType instanceof Class))
                throw new RuntimeException("请指定 ViewHolder 的泛型为 Class");
            this.itemType = (Class<?>) genericType;
            this.viewHolderClass = viewHolderClass;
        }

        public DataAdaptedHolderFactory(Class<?> itemType, Class<? extends DataAdaptedViewHolder> viewHolderClass) {
            this.itemType = itemType;
            this.viewHolderClass = viewHolderClass;
        }

        @Override
        public boolean handle(Object item, int position) {
            return itemType.isInstance(item);
        }

        @Override
        public DataAdaptedViewHolder<?> create(ViewGroup parent) {
            try {
                Constructor constructor = viewHolderClass.getConstructor(ViewGroup.class);
                constructor.setAccessible(true);
                return (DataAdaptedViewHolder<?>) constructor.newInstance(parent);
            } catch (Exception e) {
                throw new RuntimeException(viewHolderClass.getName() + " 实例化失败", e);
            }
        }
    }

    /**
     * 通过 ResId 创建 ViewHolder 的 HolderFactory
     */
    public static class ResIdHolderFactory<T> implements HolderFactory {
        private final int resId;
        private final ItemBinder<T> itemBinder;

        public ResIdHolderFactory(int resId, ItemBinder<T> itemBinder) {
            this.resId = resId;
            this.itemBinder = itemBinder;
        }

        @Override
        public boolean handle(Object item, int position) {
            return ((Class) ClassUtils.getGenericType(getClass(), ResIdHolderFactory.class)).isInstance(item);
        }

        @Override
        public DataAdaptedViewHolder<?> create(ViewGroup parent) {
            return new ResIdHolderFactory.ViewHolder(parent);
        }

        private class ViewHolder extends DataAdaptedViewHolder<T> {

            public ViewHolder(ViewGroup parent) {
                super(parent, resId);
            }

            @Override
            public void onBindItem(@NonNull T item, int position) {
                itemBinder.onBindItem(this, item, position);
            }
        }
    }


    /**
     * 条目点击事件监听器
     */
    public interface OnItemClickListener {
        void onItemClick(DataAdaptedViewHolder<?> viewHolder, Object item, int position);
    }

    /**
     * 条目长按事件监听器
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(DataAdaptedViewHolder<?> viewHolder, Object item, int position);
    }

    /**
     * ViewHolder 事件监听器
     * <p>
     * 用于与 Activity 或 Fragment 通信
     */
    public interface HolderEventListener<T> {
        /**
         * ViewHolder 事件回调
         *
         * @param viewHolder ViewHolder
         * @param type       事件类型，用于区分不同事件，可在 ViewHolder 中根据实际需求定义常量
         * @param pos        位置
         * @param item       数据
         */
        void onEvent(RecyclerView.ViewHolder viewHolder, String type, int pos, T item);
    }
}

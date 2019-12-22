package com.ruler.csw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 一定要注意 holder 的布局复用问题
 * Created by 丛 on 2018/7/26 0026.
 */
public class BaseRecyclerViewAdapter<B> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BindingViewHolder> {
    private final LayoutInflater inflate;
    private List<B> beanList;
    private int BRId;
    private int layoutId;

    /**
     * 多布局构造方法,当有多个布局时,重写onCreateViewHolder()方法,在里面指定布局
     * @param context 用来会的布局加载器
     */
    public BaseRecyclerViewAdapter(Context context) {
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        beanList = new ArrayList<>();
        this.BRId = -1;
    }

    /**
     * 单布局使用此构造方法,在构造方法中就指定布局文件
     * @param context
     * @param BRId bean类
     * @param layoutId item布局资源
     */
    public BaseRecyclerViewAdapter(Context context, int BRId, int layoutId) {
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        beanList = new ArrayList<>();
        this.BRId = BRId;
        this.layoutId = layoutId;
    }

    /**
     * 多布局需要重写此方法
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 绑定试图
        ViewDataBinding binding = DataBindingUtil.inflate(inflate, layoutId, parent, false);
        return new BindingViewHolder<>(binding);
    }

    /**
     * 多布局需要完全重写此方法
     * 单布局无需重写此方法,如果要重写,可以保留“数据填充”代码部分
     * 多布局BRId为-1,单布局存在正常BRId
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final BaseRecyclerViewAdapter.BindingViewHolder holder,
                                 final int position) {
        // 数据填充代码
        if (BRId != -1) {
            final B bean = beanList.get(position);
            holder.getBinding().setVariable(BRId, bean);
            holder.getBinding().executePendingBindings();
        }
        holder.itemView.setOnClickListener((View v) -> {
            if (itemClickListener != null)
                itemClickListener.onItemClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    // 默认从尾部添加,不需要更新位置
    public void add(int pos, B bean) {
        beanList.add(pos, bean);
        notifyItemInserted(pos);
    }

    /**从0号位置添加数据,更新位置
     * @param bean
     */
    public void addStart(B bean) {
        beanList.add(0 ,bean);
        notifyItemInserted(0);
    }

    // 从尾部添加,不需要更新位置
    public void addEnd(B bean) {
        beanList.add(bean);
        notifyItemInserted(beanList.size() - 1);
    }

    public void addAll(int pos, List<B> beanList) {
        this.beanList.addAll(pos, beanList);
        notifyDataSetChanged();
    }

    /**
     * 从0号位置添加数据
     * @param beanList
     */
    public void addAllStart(List<B> beanList) {
        this.beanList.addAll(0, beanList);
        notifyDataSetChanged();
    }

    public void addAllEnd(List<B> beanList) {
        this.beanList.addAll(beanList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (beanList.size() == 0 || position < 0 || position >= beanList.size()) // 防止数组越界异常
            return;
        beanList.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(B bean) {
        int removePos = beanList.indexOf(bean);
        remove(removePos);
    }

    public void removeAll() {
        beanList.clear();
        notifyDataSetChanged();
    }

    public void removeAll(List<B> beanList) {
        this.beanList.removeAll(beanList);
        notifyDataSetChanged();
    }

    public void set(int position, B bean) {
        this.beanList.set(position, bean);
        notifyItemChanged(position);
    }

    public List<B> getBeanList() {
        return this.beanList;
    }

    protected void setBeanList(List<B> beanList) {
        this.beanList = beanList;
    }

    protected LayoutInflater getInflate() {
        return this.inflate;
    }

    protected ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int pos);
    }

    public static class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private T binding;

        public BindingViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public T getBinding() {
            return binding;
        }
    }

}

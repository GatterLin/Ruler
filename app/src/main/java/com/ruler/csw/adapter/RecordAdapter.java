package com.ruler.csw.adapter;

import android.content.Context;

import com.ruler.csw.base.BaseRecyclerViewAdapter;
import com.ruler.csw.bean.Item;
import com.ruler.csw.databinding.ItemBinding;

/**
 * Created by 丛 on 2018/6/15 0015.
 */
public class RecordAdapter extends BaseRecyclerViewAdapter<Item> {

    public RecordAdapter(Context context, int BRId, int layoutId) {
        super(context, BRId, layoutId);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (listener != null) {
            ItemBinding binding = (ItemBinding) holder.getBinding();
            binding.cardView.setOnClickListener(v -> {
                listener.onItemClick(holder.getAdapterPosition());
            });
            binding.cardView.setOnLongClickListener(v -> {
                listener.onItemLongClick(holder.getAdapterPosition());
                return true;
            });
        }
    }

    private CardViewClickListener listener;

    public void setCardViewClickListener(CardViewClickListener listener) {
        this.listener = listener;
    }

    public interface CardViewClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}

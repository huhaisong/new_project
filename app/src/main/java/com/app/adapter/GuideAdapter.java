package com.app.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.main.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

//引导页面
public class GuideAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    public GuideAdapter(@Nullable List<Integer> data) {
        super(R.layout.item_guide_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Integer s) {
        holder.setImageResource(R.id.iv_guide_icon, s);
    }
}

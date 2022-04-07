package com.app.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;


import com.app.main.R;

import org.jetbrains.annotations.NotNull;

public class TabBottomG extends LinearLayout implements TabBottomLayoutG.OnTabSelectedListener {

    private TabBottomInfo tabInfo;
    private ImageView tabImageView;
    private ImageView tabIconView;
    private ImageView ivUnread;
    private TextView tabNameView;
    private RelativeLayout rlTabIconContainer;

    public TabBottomG(Context context) {
        this(context, null);
    }

    public TabBottomG(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBottomG(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tab_bottom, this);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.iv_main_tab_icon);
        rlTabIconContainer = findViewById(R.id.rl_tab_container);
        ivUnread = findViewById(R.id.unread_msg_number);
        tabNameView = findViewById(R.id.tv_main_tab_title);
    }

    public void setTabInfoG(@NonNull @NotNull TabBottomInfo tabBottomInfo) {
        this.tabInfo = tabBottomInfo;
        inflateInfo(false, true);
    }

    public TabBottomInfo getTabInfo() {
        return tabInfo;
    }

    public void resetHeight(@Px int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
    }

    public void setIvUnreadState(boolean isUnread) {
        if (isUnread) {
            ivUnread.setVisibility(VISIBLE);
        } else {
            ivUnread.setVisibility(GONE);
        }
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (init) {
            if (!TextUtils.isEmpty(tabInfo.getName())) {
                tabNameView.setText(tabInfo.getName());
            }
            if (!tabInfo.isCenter()) {
                tabImageView.setVisibility(GONE);
                tabIconView.setVisibility(VISIBLE);
                rlTabIconContainer.setVisibility(VISIBLE);
                tabIconView.setImageDrawable(getResources().getDrawable(tabInfo.getIconDrawable()));
            } else {
                tabImageView.setVisibility(VISIBLE);
                tabIconView.setVisibility(GONE);
                rlTabIconContainer.setVisibility(GONE);
                tabImageView.setImageDrawable(getResources().getDrawable(tabInfo.getIconDrawable()));
            }
        }
        if (selected) {
            tabImageView.setSelected(true);
            tabIconView.setSelected(true);
            tabNameView.setSelected(true);
        } else {
            tabImageView.setSelected(false);
            tabIconView.setSelected(false);
            tabNameView.setSelected(false);
        }
    }

    public void onTabSelectedChange(int index, @Nullable @org.jetbrains.annotations.Nullable TabBottomInfo prevInfo, @NonNull @NotNull TabBottomInfo nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }
}
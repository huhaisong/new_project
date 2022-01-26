package com.library_common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.library_common.R;
import com.library_common.util.SystemUtils;

public class CustomTitleBar extends RelativeLayout {
    private Context mContext;

    //需否需要与通知栏保持高度
    private boolean headerTopShow = true;
    private RelativeLayout rlTitle;
    private TextView tvTitle;

    private LinearLayout llBack;

    private OnClickListener backListener;
    private OnClickListener rightClick;

    private String title;

    private int titleColor;
    private FrameLayout frameLayout;
    private TextView tvRight;

    public CustomTitleBar(Context context) {
        this(context, null);
        this.mContext = context;
        initView(null);
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(attrs);
        initData();
        initEvent();
    }

    public CustomTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs);
        initData();
        initEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initView(attrs);
        initData();
        initEvent();
    }


    private void initData() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlTitle.getLayoutParams();
        int marginTop = 0;
        if (headerTopShow) {
            marginTop = SystemUtils.getStatusHeight(mContext);
        }
        params.setMargins(0, marginTop, 0, 0);

    }


    private void initView(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        LayoutInflater.from(mContext).inflate(R.layout.header_title_layout, this);
        TypedArray attributes = mContext.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);

        title = attributes.getString(R.styleable.CustomTitleBar_title);
        titleColor = attributes.getColor(R.styleable.CustomTitleBar_title_color, 0xff000000);
        int backColor = attributes.getColor(R.styleable.CustomTitleBar_back_view_color, 0xff000000);
        int backgroundColor = attributes.getColor(R.styleable.CustomTitleBar_backgroundColor, 0xffffffff);
        int rightColor = attributes.getColor(R.styleable.CustomTitleBar_right_color, 0xffffffff);
        String rightTitle = attributes.getString(R.styleable.CustomTitleBar_right_title);
        int bgRight = attributes.getResourceId(R.styleable.CustomTitleBar_right_drawable, R.drawable.bg_80_white_radius_5);
        attributes.recycle();

        ImageView ivBack = findViewById(R.id.iv_back);
        frameLayout = findViewById(R.id.frame_layout);
        tvTitle = findViewById(R.id.tv_title);
        rlTitle = findViewById(R.id.rl_title_bar);
        llBack = findViewById(R.id.ll_left_layout);
        tvRight = findViewById(R.id.tv_right);

        if (!TextUtils.isEmpty(rightTitle)) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightTitle);
            tvRight.setTextColor(rightColor);
            tvRight.setBackgroundResource(bgRight);
        }

        frameLayout.setBackgroundColor(backgroundColor);
        ivBack.setColorFilter(backColor);
        tvTitle.setText(title);
        tvTitle.setTextColor(titleColor);
    }

    private void initEvent() {
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != rightClick) {
                    rightClick.onClick(v);
                }
            }
        });

        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != backListener) {
                    backListener.onClick(view);
                } else {
                    ((Activity) getContext()).onBackPressed();
                }
            }
        });

    }

    public void setBackListener(OnClickListener backListener) {
        this.backListener = backListener;
    }

    public void addRightClickListener(OnClickListener listener) {
        this.rightClick = listener;
    }

    public void setTitle(String title) {
        this.title = title;
        if (null != tvTitle) {
            tvTitle.setText(title);
        }
    }

    public String getTitle() {
        return title;
    }
}
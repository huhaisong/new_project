package com.library_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library_common.R;

//倒计时自定义View
public class GetCodeView extends FrameLayout {
    private static final int MAX_TIME = 60;
    private Context mContext;

    private Handler mHandler = new Handler();

    private int runTime = 0;
    private TextView tvGetCode;

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.removeCallbacks(this);
            if (runTime == MAX_TIME) {
                setEnabled(true);
                tvGetCode.setText("获取验证码");
            } else {
                ++runTime;
                tvGetCode.setText(MAX_TIME - runTime + "s");
                mHandler.postDelayed(timeRunnable, 1000);
            }
        }
    };
    private int txtDefColor;
    private int txtSelectColor;

    public GetCodeView(@NonNull Context context) {
        super(context);
        this.mContext = context;

        initView(null);
    }

    public GetCodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initView(attrs);
    }

    public GetCodeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.get_code_layout, this, true);

        TypedArray attributes = mContext.obtainStyledAttributes(attrs, R.styleable.GetCodeView);
        txtDefColor = attributes.getColor(R.styleable.GetCodeView_txt_color_def, 0xffffffff);
        txtSelectColor = attributes.getColor(R.styleable.GetCodeView_txt_color_select, 0xffffffff);
        int textSize = attributes.getInt(R.styleable.GetCodeView_txt_size, 14);


        tvGetCode = findViewById(R.id.tv_get_code2);
        tvGetCode.setTextColor(txtDefColor);
        tvGetCode.setTextSize(textSize);
        tvGetCode.setText("获取验证码");
    }

    public void onStartRunnable() {
        setEnabled(false);
        runTime = 0;
        tvGetCode.setTextColor(txtSelectColor);
        mHandler.post(timeRunnable);
    }

    public void onCancelRunnable() {
        setEnabled(true);
        tvGetCode.setText("获取验证码");
        tvGetCode.setTextColor(txtDefColor);
        mHandler.removeCallbacks(timeRunnable);
    }

    public void onDestroy() {
        if (null != mHandler) {
            mHandler.removeCallbacks(timeRunnable);
        }
    }
}

package com.library_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.library_common.R;


/**
 * 自定义状态页面
 */
public class StatusView extends RelativeLayout {
    private Context mContext;
    private ImageView ivLoading;
    private LinearLayout llLoading;
    private LinearLayout emptyLayout;
    private LinearLayout errorLayout;
    private TextView tvEmpty;
    private TextView tvError;
    private TextView tvRequest;

    private OnErrorRequestListener onErrorRequestListener;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initView(attrs);

        initEvent();
    }

    private void initEvent() {
        tvRequest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onErrorRequestListener){
                    onErrorRequestListener.onRequest();
                }
            }
        });
    }

    public void showError(String strError) {
        hideAllLayout();
        this.setVisibility(View.VISIBLE);
        if (errorLayout != null) {
            errorLayout.setVisibility(View.VISIBLE);
            tvError.setText(strError);
        }
    }

    public void showEmpty(String emptyMsg) {
        hideAllLayout();
        this.setVisibility(View.VISIBLE);
        if (emptyLayout != null) {
            emptyLayout.setVisibility(View.VISIBLE);
            tvEmpty.setText(emptyMsg);
        }
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.page_status_layout, this);
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.StatusView);
        int backgroundColor = array.getColor(R.styleable.StatusView_bg_color,0xffffffff);

        llLoading = findViewById(R.id.ll_loading_layout);
        ivLoading = findViewById(R.id.iv_loading_view);

        emptyLayout = findViewById(R.id.ll_empty_layout);
        tvEmpty = findViewById(R.id.tv_empty_title);

        errorLayout = findViewById(R.id.ll_error_layout);
        tvError = findViewById(R.id.tv_error_title);
        tvRequest = findViewById(R.id.tv_request);
        this.setClickable(true);
        this.setBackgroundColor(backgroundColor);
        this.setVisibility(View.GONE);
    }

    public void showLoading() {
        hideAllLayout();
        this.setVisibility(View.VISIBLE);
        if (ivLoading != null) {
            if (ivLoading.getAnimation() != null) {
                ivLoading.getAnimation().cancel();
            }
            startLoadingAni();
            llLoading.setVisibility(View.VISIBLE);
        }
    }

    private void startLoadingAni() {
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.loading_ani);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        ivLoading.startAnimation(operatingAnim);
    }

    public void hideAllLayout() {
        this.setVisibility(GONE);
        llLoading.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    public void hideLoading() {
        if (ivLoading != null) {
            if (ivLoading.getAnimation() != null) {
                ivLoading.getAnimation().cancel();
            }
            llLoading.setVisibility(View.GONE);
            this.setVisibility(View.GONE);
        }
    }

    public interface OnErrorRequestListener{
        void onRequest();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE){
            if (llLoading!=null &&llLoading.getVisibility() == View.VISIBLE ){
                startLoadingAni();
            }
            //开始某些任务
        }
        else if(visibility == INVISIBLE || visibility == GONE){

            //停止某些任务
        }
    }

    public void setOnErrorRequestListener(OnErrorRequestListener onErrorRequestListener) {
        this.onErrorRequestListener = onErrorRequestListener;
    }
}

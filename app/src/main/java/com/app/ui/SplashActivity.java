package com.app.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.main.BR;
import com.app.main.R;
import com.app.main.databinding.ActivitySplashLayoutBinding;
import com.app.viewmodel.SplashViewModel;
import com.library_common.base.BaseActivity;
import com.library_common.util.ARouterUtils;
import com.library_common.util.MMKVUtil;

/**
 * 启动页
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashLayoutBinding, SplashViewModel> {
    private Handler mHandler = new Handler();
    //启动页进行时间
    private static final long SPLASH_TIME = 5000L;
    private long inTime = 0L;

    private static final boolean firstOpenApp = MMKVUtil.getIsFirstTime();

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            //首次进入app
            if (firstOpenApp) {
                GuideActivity.goActivity(SplashActivity.this);
                finish();
            } else {
                mHandler.removeCallbacks(timeRunnable);
                if (inTime == SPLASH_TIME) {
                    ARouterUtils.toMAINActivity();
                    finish();
                } else {
                    mHandler.postDelayed(timeRunnable, 1000L);
                    inTime += 1000L;
                    mBinding.tvSplashFinish.setText((int) ((SPLASH_TIME - inTime) / 1000) + "s");
                }
            }
        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash_layout;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {
        mBinding.ivSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBinding.tvSplashFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(timeRunnable);
                ARouterUtils.toMAINActivity();
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.tvSplashFinish.setText((int) ((SPLASH_TIME - inTime) / 1000) + "s");
        mHandler.postDelayed(timeRunnable, 1000L);
        mBinding.tvSplashFinish.setVisibility(View.VISIBLE);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(timeRunnable);
        mHandler = null;
    }
}
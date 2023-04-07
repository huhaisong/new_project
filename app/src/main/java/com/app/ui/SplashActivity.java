package com.app.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import com.app.main.BR;
import com.app.main.BuildConfig;
import com.app.main.R;
import com.app.main.databinding.ActivitySplashLayoutBinding;
import com.app.viewmodel.SplashViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.library_common.base.BaseActivity;
import com.library_common.http.ResultData;
import com.library_common.util.ARouterUtils;
import com.library_common.util.ImgLoaderUtils;
import com.library_common.util.MMKVUtil;
import com.library_common.util.StatusBarUtil;
import com.library_common.util.SystemUtils;

import java.util.Objects;

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

            mHandler.removeCallbacks(timeRunnable);
            if (inTime == SPLASH_TIME) {
                toMainActivity();
            } else {
                mHandler.postDelayed(timeRunnable, 1000L);
                inTime += 1000L;
                mBinding.tvSplashFinish.setText((int) ((SPLASH_TIME - inTime) / 1000) + "s");
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
        StatusBarUtil.setTransparentForWindow(this);
        mBinding.ivSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBinding.tvSplashFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(timeRunnable);
                toMainActivity();
            }
        });
    }

    private void toMainActivity() {
        if (SystemUtils.isOpenProxyHost(mContext)
                && !BuildConfig.DEBUG) {
            showShortToast(getString(R.string.please_close_vpn_and_open_again));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 100);
        } else {
            ARouterUtils.toMAINActivity();
            finish();
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (firstOpenApp) {
            GuideActivity.goActivity(SplashActivity.this);
            finish();
        } else {
            mViewModel.getSplashImg();
        }
    }

    @Override
    public void initViewObservable() {
        mViewModel.splashUrlData.observe(this, this::handleGetSplashUrl);
    }

    private void handleGetSplashUrl(ResultData<String> stringResultData) {
        String url = "https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500";
        Glide.with(this).asDrawable().load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                startTimeCut();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                startTimeCut();
                loadRemoteSplash(drawable);
                return true;
            }
        }).submit();
    }

    private void loadRemoteSplash(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBinding.ivSplash.setVisibility(View.GONE);
                mBinding.llRemoteSplash.setVisibility(View.VISIBLE);
                mBinding.ivSplashBottom.setImageResource(R.mipmap.bg_splash_bottom);
                mBinding.ivSplashTop.setImageDrawable(drawable);
            }
        });

    }

    private void startTimeCut() {
        mBinding.tvSplashFinish.setText((int) ((SPLASH_TIME - inTime) / 1000) + "s");
        mHandler.postDelayed(timeRunnable, 1000L);
        mBinding.tvSplashFinish.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(timeRunnable);
        mHandler = null;
    }
}
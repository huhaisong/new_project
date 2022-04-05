package com.app.ui;

/**
 * 引导页
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.app.adapter.GuideAdapter;
import com.app.main.BR;
import com.app.main.R;
import com.app.main.databinding.ActivityGuideBinding;
import com.library_common.base.BaseActivity;
import com.library_common.base.BaseViewModel;
import com.library_common.util.ARouterUtils;
import com.library_common.util.MMKVUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity<ActivityGuideBinding, BaseViewModel> {

    private List<Integer> guideList = new ArrayList<>();

    private ViewPager2.OnPageChangeCallback pageChangeListener = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            //是否是最后一张
            boolean isFinish = position == guideList.size() - 1;
            mBinding.tvSuccessGoIn.setVisibility(isFinish ? View.VISIBLE : View.GONE);

            View indicatorView = mBinding.llIndicatorLayout.getChildAt(position);
            if (indicatorView != null) {
                for (int i = 0; i < mBinding.llIndicatorLayout.getChildCount(); i++) {
                    mBinding.llIndicatorLayout.getChildAt(i).setSelected(false);
                }
                indicatorView.setSelected(true);
            }
        }
    };

    public static void goActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }


    //添加指示器
    private void addIndicatorView() {
        if (!guideList.isEmpty()) {
            mBinding.llIndicatorLayout.setWeightSum(guideList.size());
            for (int i = 0; i < guideList.size(); i++) {
                ImageView indicatorView = new ImageView(getBaseContext());
                indicatorView.setBackgroundResource(R.drawable.guide_indicator_layout);
                mBinding.llIndicatorLayout.addView(indicatorView);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicatorView.getLayoutParams();
                params.setMargins(15, 0, 15, 0);
                indicatorView.setLayoutParams(params);
            }
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_guide;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {
        mBinding.vpGuide.registerOnPageChangeCallback(pageChangeListener);

        mBinding.tvSuccessGoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MMKVUtil.setIsFirstTime(false);
                ARouterUtils.toMAINActivity();
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        guideList.clear();
        guideList.add(R.drawable.guide1_v1);
        guideList.add(R.drawable.guide2_v1);
        guideList.add(R.drawable.guide3_v1);
        GuideAdapter guideAdapter = new GuideAdapter(guideList);
        mBinding.vpGuide.setAdapter(guideAdapter);
        addIndicatorView();
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.vpGuide.unregisterOnPageChangeCallback(pageChangeListener);
    }
}
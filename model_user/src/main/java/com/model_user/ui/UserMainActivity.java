package com.model_user.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.model_user.BR;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;
import com.library_common.util.ARouterUtils;
import com.model_user.R;
import com.model_user.databinding.ActivityUserMainBinding;
import com.model_user.viewmodel.UserMainViewModel;

@Route(path = RouterPath.User.ACTIVITY_MAIN)
public class UserMainActivity extends BaseActivity<ActivityUserMainBinding, UserMainViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_user_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mViewModel.getBaiduInfo();
    }

    @Override
    public void initViewObservable() {
        mViewModel.getBaidu.observe(this, this::handleGetBaidu);
    }

    private static final String TAG = "UserMainActivity";

    private void handleGetBaidu(String stringResultData) {
        mBinding.tvContent.setText(stringResultData);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {

    }

    public void GoToExample(View view) {
        ARouterUtils.toExampleActivity();
    }
}
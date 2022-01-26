package com.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;
import com.myapplication.databinding.ActivityExampleMainBinding;
import com.myapplication.viewmodel.ExampleViewModel;

@Route(path = RouterPath.Example.ACTIVITY_EXAMPLE_MAIN)
public class ExampleActivity extends BaseActivity<ActivityExampleMainBinding, ExampleViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_example_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mViewModel.getBaiduInfo();
        mBinding.btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ARouter.getInstance().build(RouterPath.User.ACTIVITY_MAIN).navigation();
            }
        });
    }

    @Override
    public void initViewObservable() {
    }

    private static final String TAG = "ExampleActivity";

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }
}

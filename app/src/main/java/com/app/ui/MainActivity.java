package com.app.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.app.main.BR;
import com.app.main.R;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;

@Route(path = RouterPath.Main.ACTIVITY_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
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

    }

    @Override
    public void initViewObservable() {

    }
}

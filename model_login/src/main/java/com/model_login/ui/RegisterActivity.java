package com.model_login.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;
import com.model_login.BR;
import com.model_login.R;
import com.model_login.databinding.ActivityRegisterBinding;
import com.model_login.viewmodel.RegisterViewModel;

@Route(path = RouterPath.Login.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
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

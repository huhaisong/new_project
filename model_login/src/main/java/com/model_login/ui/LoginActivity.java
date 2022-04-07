package com.model_login.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;
import com.library_common.util.ARouterUtils;
import com.model_login.BR;
import com.model_login.R;
import com.model_login.databinding.ActivityLoginBinding;
import com.model_login.viewmodel.LoginViewModel;

@Route(path = RouterPath.Login.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {
        mBinding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.btnLogin.setEnabled(s.length() >= 11 &&
                        !TextUtils.isEmpty(mBinding.etPwd.getText()) &&
                        mBinding.etPwd.getText().length() >= 6);
                mBinding.ibtnDeletePhone.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.btnLogin.setEnabled(mBinding.etPhone.getText().length() >= 11 &&
                        !TextUtils.isEmpty(s) &&
                        s.length() >= 6);
                mBinding.ibtnDeletePwd.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.toRegisterActivity();
            }
        });
//        mBinding.tvForget.setOnClickListener(v -> startActivity(ForgetActivity.class));
        mBinding.ibtnDeletePhone.setOnClickListener(v -> mBinding.etPhone.setText(""));
        mBinding.ibtnDeletePwd.setOnClickListener(v -> mBinding.etPwd.setText(""));
        mBinding.cbPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etPwd.setInputType(isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {

    }
}

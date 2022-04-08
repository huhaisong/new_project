package com.model_login.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;
import com.model_login.BR;
import com.model_login.R;
import com.model_login.databinding.ActivityChangePasswordBinding;
import com.model_login.viewmodel.ChangePasswordViewModel;

@Route(path = RouterPath.Login.ACTIVITY_CHANGE_PASSWORD)
public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding, ChangePasswordViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_change_password;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {
        mBinding.etConfirmPwd.addTextChangedListener(new CustomTextWatch() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                mBinding.ibtnConfirmDeletePwd.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }
        });
        mBinding.etPwd.addTextChangedListener(new CustomTextWatch() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                mBinding.ibtnDeletePwd.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);

            }
        });
        mBinding.etNewPwd.addTextChangedListener(new CustomTextWatch() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                mBinding.ibtnNewDeletePwd.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }
        });

        mBinding.ibtnDeletePwd.setOnClickListener(v -> mBinding.etPwd.setText(""));
        mBinding.ibtnConfirmDeletePwd.setOnClickListener(v -> mBinding.etConfirmPwd.setText(""));
        mBinding.ibtnNewDeletePwd.setOnClickListener(v -> mBinding.etNewPwd.setText(""));

        mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBinding.etNewPwd.getText().toString().equals(mBinding.etConfirmPwd.getText().toString())) {
                    showShortToast("新密码和确认密码需要一直");
                } else {
                    mViewModel.changePassword(mBinding.etNewPwd.getText().toString(), mBinding.etPwd.getText().toString());
                }
            }
        });

        mBinding.cbPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etPwd.setInputType(isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        mBinding.cbConfirmPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etConfirmPwd.setInputType(isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        mBinding.cbNewPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etNewPwd.setInputType(isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {
        mViewModel.changePasswordData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showShortToast("修改成功");
                mBinding.etNewPwd.setText("");
                mBinding.etPwd.setText("");
                mBinding.etConfirmPwd.setText("");
            }
        });
    }


    private void checkRegisterEnable() {
        mBinding.btnConfirm.setEnabled(!TextUtils.isEmpty(mBinding.etPwd.getText()) && mBinding.etPwd.getText().length() >= 6 &&
                !TextUtils.isEmpty(mBinding.etConfirmPwd.getText()) && mBinding.etConfirmPwd.getText().length() >= 6 &&
                !TextUtils.isEmpty(mBinding.etNewPwd.getText()) && mBinding.etNewPwd.getText().length() >= 6
        );
    }

    private class CustomTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkRegisterEnable();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

package com.model_user.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseActivity;
import com.library_common.router.RouterPath;
import com.model_user.BR;
import com.model_user.R;
import com.model_user.databinding.ActivityVerifyIdBinding;
import com.model_user.viewmodel.VerifyIDViewModel;

@Route(path = RouterPath.User.ACTIVITY_VERIFY_ID)
public class VerifyIDActivity extends BaseActivity<ActivityVerifyIdBinding, VerifyIDViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_verify_id;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {
        mBinding.etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.btnConfirm.setEnabled(!TextUtils.isEmpty(mBinding.etNumber.getText().toString()) &&
                        !TextUtils.isEmpty(mBinding.etName.getText().toString())
                );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.btnConfirm.setEnabled(!TextUtils.isEmpty(mBinding.etNumber.getText().toString()) &&
                        !TextUtils.isEmpty(mBinding.etName.getText().toString())
                );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.verifyID(mBinding.etName.getText().toString(), mBinding.etNumber.getText().toString());
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {

    }
}

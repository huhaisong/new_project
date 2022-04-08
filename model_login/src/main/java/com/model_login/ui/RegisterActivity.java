package com.model_login.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.library_common.base.BaseActivity;
import com.library_common.base.BaseModel;
import com.library_common.router.RouterPath;
import com.library_common.util.DeviceIdUtils;
import com.library_common.util.ImgLoaderUtils;
import com.model_login.BR;
import com.model_login.R;
import com.model_login.databinding.ActivityRegisterBinding;
import com.model_login.viewmodel.RegisterViewModel;

@Route(path = RouterPath.Login.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {


    private TextWatcher textWatcher = new TextWatcher() {
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
    };

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
        mBinding.etUserName.addTextChangedListener(textWatcher);
        mBinding.etPwd.addTextChangedListener(textWatcher);
        mBinding.etMobile.addTextChangedListener(textWatcher);
        mBinding.etCode.addTextChangedListener(textWatcher);

        mBinding.ivBack.setOnClickListener(v -> finish());

        mBinding.ivVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVerifyPic();
            }
        });

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.register(mBinding.etUserName.getText().toString(),
                        mBinding.etPwd.getText().toString(),
                        mBinding.etMobile.getText().toString(),
                        mBinding.etCode.getText().toString(),
                        mBinding.etInviteCode.getText().toString());
            }
        });
    }

    private void loadVerifyPic() {
        GlideUrl glideUrl = new GlideUrl(BaseModel.domain + "/verify/generate/anonymous?type=2", new LazyHeaders.Builder()
                .addHeader("deviceId", DeviceIdUtils.getDeviceId(mContext))
                .addHeader("Content-Type", "application/x-www-urlencoded")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("client-type", "Android")
                .build());

        ImgLoaderUtils.Load(mBinding.ivVerifyCode, glideUrl);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadVerifyPic();
    }

    @Override
    public void initViewObservable() {
        mViewModel.registerData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showShortToast("注册成功");
                mBinding.etCode.setText("");
                mBinding.etUserName.setText("");
                mBinding.etInviteCode.setText("");
                mBinding.etPwd.setText("");
                mBinding.etMobile.setText("");
            }
        });
    }

    private void checkRegisterEnable() {
        mBinding.btnLogin.setEnabled(!TextUtils.isEmpty(mBinding.etUserName.getText()) &&
                !TextUtils.isEmpty(mBinding.etPwd.getText()) && mBinding.etPwd.getText().length() >= 6 &&
                !TextUtils.isEmpty(mBinding.etCode.getText()) && mBinding.etCode.getText().length() == 6);
    }
}

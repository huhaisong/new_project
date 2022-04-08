package com.model_login.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.library_common.MyApplication;
import com.library_common.base.BaseActivity;
import com.library_common.base.BaseModel;
import com.library_common.http.Android10DownloadFactory;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpDownLoadCallBack;
import com.library_common.http.ResultData;
import com.library_common.router.RouterPath;
import com.library_common.util.ARouterUtils;
import com.library_common.util.DeviceIdUtils;
import com.library_common.util.ImgLoaderUtils;
import com.library_common.util.MMKVUtil;
import com.model_login.BR;
import com.model_login.R;
import com.model_login.databinding.ActivityLoginBinding;
import com.model_login.viewmodel.LoginViewModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rxhttp.wrapper.entity.Progress;

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

    private Handler wxHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bitmap wxShareBitmap = (Bitmap) msg.obj;
                    mBinding.ivVerifyCode.setImageBitmap(wxShareBitmap);
//                    ImgLoaderUtils.Load(mBinding.ivVerifyCode, glideUrl);

                    break;
            }
        }
    };

    @Override
    protected void initLister() {
        mBinding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.btnLogin.setEnabled(!TextUtils.isEmpty(mBinding.etPwd.getText()) &&
                        !TextUtils.isEmpty(mBinding.etCode.getText()) && mBinding.etCode.getText().length() == 6 &&
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
                mBinding.btnLogin.setEnabled(!TextUtils.isEmpty(mBinding.etUserName.getText().toString()) &&
                        !TextUtils.isEmpty(mBinding.etCode.getText()) && mBinding.etCode.getText().length() == 6 &&
                        mBinding.etPwd.getText().toString().length() >= 6
                );
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
        mBinding.ivVerifyCode.setOnClickListener(v -> loadVerifyPic());
//        mBinding.tvForget.setOnClickListener(v -> startActivity(ForgetActivity.class));
        mBinding.ibtnDeletePhone.setOnClickListener(v -> mBinding.etUserName.setText(""));
        mBinding.ibtnDeletePwd.setOnClickListener(v -> mBinding.etPwd.setText(""));
        mBinding.cbPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etPwd.setInputType(isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));

        mBinding.autoLoginCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> MMKVUtil.setIsAutoLogin(isChecked));
        mBinding.rememberPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> MMKVUtil.setIsRememberPassword(isChecked));

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.login(mBinding.etUserName.getText().toString(), mBinding.etPwd.getText().toString(), mBinding.etCode.getText().toString());
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadVerifyPic();
        mBinding.autoLoginCheckbox.setChecked(MMKVUtil.getIsAutoLogin());
        mBinding.rememberPasswordCheckbox.setChecked(MMKVUtil.getIsRememberPassword());
    }

    private void loadVerifyPic() {
        GlideUrl glideUrl = new GlideUrl(BaseModel.domain + "/verify/generate/anonymous?type=1", new LazyHeaders.Builder()
                .addHeader("deviceId", DeviceIdUtils.getDeviceId(mContext))
                .addHeader("Content-Type", "application/x-www-urlencoded")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("client-type", "Android")
                .build());

        ImgLoaderUtils.Load(mBinding.ivVerifyCode, glideUrl);
    }


    @Override
    public void initViewObservable() {
        mViewModel.loginData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showShortToast("登录成功");
            }
        });
    }
}

package com.model_login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.library_common.base.BaseViewModel;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpCallBack;
import com.library_common.http.ResultData;
import com.model_login.model.LoginModel;

public class RegisterViewModel extends BaseViewModel<LoginModel> {

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.mModel = LoginModel.getInstance();
    }

    public MutableLiveData<String> getBaidu = new MutableLiveData<>();
    public MutableLiveData<String> loginData = new MutableLiveData<>();
    public MutableLiveData<ResultData<String>> codeResult = new MutableLiveData<>();

    public void getBaiduInfo() {
        onScopeStart(mModel.getBaiduContent(new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                getBaidu.postValue(data);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {

            }
        }));
    }

    public void getCode(String phoneCode, String strPhone, String s) {
        onScopeStart(mModel.getCodeInOffsite(phoneCode, strPhone, s, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                ResultData<String> strData = new ResultData<>();
                strData.setData("发送成功");
                codeResult.postValue(strData);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                ResultData<String> strData = new ResultData<>();
                strData.setErrorMsg(errorInfo.getErrorCode(), errorInfo.getErrorMsg());
                codeResult.postValue(strData);
                showShortToast(errorInfo.getErrorMsg());
            }
        }));
    }
}

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

    public MutableLiveData<String> registerData = new MutableLiveData<>();

    public void register(String userName, String password, String mobile, String code, String inviteCode) {
        onScopeStart(mModel.register(userName, password, mobile, code, inviteCode, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                registerData.postValue(data);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                showShortToast(errorInfo.getErrorMsg());
            }
        }));
    }


}

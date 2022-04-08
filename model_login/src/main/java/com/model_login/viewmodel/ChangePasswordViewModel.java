package com.model_login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.library_common.base.BaseViewModel;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpCallBack;
import com.library_common.http.ResultData;
import com.model_login.model.LoginModel;

public class ChangePasswordViewModel extends BaseViewModel<LoginModel> {

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        this.mModel = LoginModel.getInstance();
    }

    public MutableLiveData<String> changePasswordData = new MutableLiveData<>();

    public void changePassword(String newPassword, String password) {
        onScopeStart(mModel.changePassword(newPassword, password, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                changePasswordData.postValue(data);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                showShortToast(errorInfo.getErrorMsg());
            }
        }));
    }
}

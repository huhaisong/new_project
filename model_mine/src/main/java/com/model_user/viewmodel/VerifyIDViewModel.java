package com.model_user.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.library_common.base.BaseViewModel;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpCallBack;
import com.model_user.model.UserMainModel;

public class VerifyIDViewModel extends BaseViewModel<UserMainModel> {

    public VerifyIDViewModel(@NonNull Application application) {
        super(application);
        this.mModel = UserMainModel.getInstance();
    }

    public MutableLiveData<String> verifyIDData = new MutableLiveData<>();

    public void verifyID(String name, String number) {
        onScopeStart(mModel.verifyID(name, number, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                verifyIDData.postValue(data);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                showShortToast(errorInfo.getErrorMsg());
            }
        }));
    }
}

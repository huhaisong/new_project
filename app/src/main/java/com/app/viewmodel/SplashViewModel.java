package com.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.app.model.MainModel;
import com.library_common.base.BaseViewModel;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpCallBack;
import com.model_user.model.UserMainModel;

public class SplashViewModel extends BaseViewModel<MainModel> {

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.mModel = MainModel.getInstance();
    }

    public MutableLiveData<String> getBaidu = new MutableLiveData<>();

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
}

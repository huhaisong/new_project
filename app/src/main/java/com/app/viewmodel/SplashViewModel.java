package com.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.app.model.MainModel;
import com.library_common.base.BaseViewModel;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpCallBack;
import com.library_common.http.ResultData;
import com.model_user.model.UserMainModel;

public class SplashViewModel extends BaseViewModel<MainModel> {

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.mModel = MainModel.getInstance();
    }

    public MutableLiveData<ResultData<String>> splashUrlData = new MutableLiveData<>();

    public void getSplashImg() {
        ResultData<String> strData = new ResultData<>();
        strData.setData("data");
        splashUrlData.postValue(strData);

       /* onScopeStart(mModel.getSplashUrl(new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                ResultData<String> strData = new ResultData<>();
                strData.setData(data);
                splashUrlData.postValue(strData);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                ResultData<String> strData = new ResultData<>();
                strData.setErrorMsg(errorInfo.getErrorCode(), errorInfo.getErrorMsg());
                splashUrlData.postValue(strData);
                showShortToast(errorInfo.getErrorMsg());
            }
        }));*/
    }
}

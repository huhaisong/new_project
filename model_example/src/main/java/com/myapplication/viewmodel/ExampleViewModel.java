package com.myapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.library_common.base.BaseViewModel;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpCallBack;
import com.myapplication.model.ExampleModel;

public class ExampleViewModel extends BaseViewModel<ExampleModel> {

    private static final String TAG = "ExampleViewModel";

    public ExampleViewModel(@NonNull Application application) {
        super(application);
        this.mModel = ExampleModel.getInstance();
    }

    public MutableLiveData<String> getBaidu = new MutableLiveData<>();

    public void getBaiduInfo() {
        onScopeStart(mModel.getBaiduContent(new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getBaidu.postValue(data);
                    }
                }).start();
            }

            @Override
            public void onError(ErrorInfo errorInfo) {

            }
        }));
    }
}

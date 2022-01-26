package com.myapplication.model;

import com.library_common.base.BaseModel;
import com.library_common.http.HttpCallBack;
import com.library_common.http.OnError;

import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.RxHttp;

public class ExampleModel extends BaseModel {
    private volatile static ExampleModel INSTANCE = null;

    public static ExampleModel getInstance() {
        if (INSTANCE == null) {
            synchronized (ExampleModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ExampleModel();
                }
            }
        }
        return INSTANCE;
    }

    public Disposable getBaiduContent(HttpCallBack<String> callBack) {
        return RxHttp.get("https://www.baidu.com/")
                .asClass(String.class)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }
}

package com.app.model;

import com.library_common.base.BaseModel;
import com.library_common.http.HttpCallBack;
import com.library_common.http.OnError;

import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.RxHttp;

public class MainModel extends BaseModel {
    private volatile static MainModel INSTANCE = null;

    public static MainModel getInstance() {
        if (INSTANCE == null) {
            synchronized (MainModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainModel();
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

    public Disposable getSplashUrl(HttpCallBack<String> callBack) {
        return RxHttp.get(getCommonBaseUrl()+"/splash_url")
                .asClass(String.class)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }
}

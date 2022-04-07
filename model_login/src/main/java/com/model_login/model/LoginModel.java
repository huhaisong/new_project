package com.model_login.model;

import com.library_common.base.BaseModel;
import com.library_common.http.HttpCallBack;
import com.library_common.http.OnError;

import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.RxHttp;

public class LoginModel extends BaseModel {
    private volatile static LoginModel INSTANCE = null;

    public static LoginModel getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginModel();
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

    public Disposable getCodeInOffsite(String phoneCode, String username, String type, HttpCallBack<String> callBack) {
        return RxHttp.postJson(getCommonBaseUrl() + "/support/sms/send")
                .add("areaCode", phoneCode)
                .add("username", username)
                .add("type", type)
                .asResponse(String.class)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }
}

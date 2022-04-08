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

    //type：1登录2注册
    public Disposable getImageCode(String type, HttpCallBack<String> callBack) {
        return RxHttp.get(getCommonBaseUrl() + "/verify/generate/anonymous")
                .add("type", type)
                .asResponse(String.class)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }

    public Disposable login(String userName, String password, String code, HttpCallBack<String> callBack) {
        return RxHttp.postJson(getCommonBaseUrl() + "/login?username=" + userName + "&password=" + password + "&code=" + code)
                .asResponse(String.class)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }

    public Disposable register(String userName, String password, String mobile, String code, String inviteCode, HttpCallBack<String> callBack) {
        return RxHttp.postJson(getCommonBaseUrl() + "/register")
                .add("username", userName)
                .add("password", password)
                .add("code", code)
                .add("mobile", mobile)
                .add("inviteCode", inviteCode)
                .asResponse(String.class)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }

}

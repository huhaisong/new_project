package com.model_user.model;

import com.library_common.base.BaseModel;
import com.library_common.http.HttpCallBack;
import com.library_common.http.OnError;

import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.RxHttp;

public class UserMainModel extends BaseModel {
    private volatile static UserMainModel INSTANCE = null;

    public static UserMainModel getInstance() {
        if (INSTANCE == null) {
            synchronized (UserMainModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserMainModel();
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

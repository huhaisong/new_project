package com.library_common.base;

import android.net.Uri;

import com.library_common.http.Android10DownloadFactory;
import com.library_common.http.HttpDownLoadCallBack;
import com.library_common.http.OnError;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.RxHttp;

/**
 * Created by goldze on 2017/6/15.
 */
public class BaseModel {

    public Disposable drownFile(Android10DownloadFactory factory, String url, HttpDownLoadCallBack<Uri> callBack) {
        return RxHttp.get(url)
                .asAppendDownload(factory, AndroidSchedulers.mainThread(), callBack::onProgress)
                .subscribe(callBack::onSuccess, (OnError) callBack::onError);
    }

    public BaseModel() {
    }
}

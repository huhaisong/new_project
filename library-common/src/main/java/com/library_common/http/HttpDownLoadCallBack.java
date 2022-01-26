package com.library_common.http;

import rxhttp.wrapper.entity.Progress;

public interface HttpDownLoadCallBack<T> {
    void onSuccess(T data);

    void onProgress(Progress progress);

    void onError(ErrorInfo errorInfo);
}

package com.library_common.http;

public interface HttpCallBack<T> {
    public void onSuccess(T data);

    public void onError(ErrorInfo errorInfo);
}

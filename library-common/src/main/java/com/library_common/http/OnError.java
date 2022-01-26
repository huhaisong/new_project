package com.library_common.http;

import io.reactivex.rxjava3.functions.Consumer;

public interface OnError extends Consumer<Throwable> {

    @Override
    default void accept(Throwable throwable) {
        onError(new ErrorInfo(throwable));
    }

    void onError(ErrorInfo error);
}
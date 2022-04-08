package com.library_common.base;

import android.app.Application;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.library_common.MyApplication;
import com.library_common.http.Android10DownloadFactory;
import com.library_common.http.ErrorInfo;
import com.library_common.http.HttpDownLoadCallBack;
import com.library_common.http.OnError;
import com.library_common.widget.StatusFunction;
import com.rxjava.rxlife.ScopeViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.RxHttp;
import rxhttp.wrapper.entity.Progress;

public class BaseViewModel<M extends BaseModel> extends ScopeViewModel {
    protected M mModel;
    private UIChangeLiveData uc;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    public void drownFile(Android10DownloadFactory factory, String url, HttpDownLoadCallBack<Uri> callBack) {
        onScopeStart(mModel.drownFile(factory, url, new HttpDownLoadCallBack<Uri>() {
            @Override
            public void onSuccess(Uri data) {
                callBack.onSuccess(data);
            }

            @Override
            public void onProgress(Progress progress) {
                callBack.onProgress(progress);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                callBack.onError(errorInfo);
            }
        }));
    }

    public void registerRxBus() {
      /*  mSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);*/
    }

    public void removeRxBus() {
        //将订阅者从管理站中移除
//        RxSubscriptions.remove(mSubscription);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.setValue(null);
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.setValue(null);
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
        public static String REQUEST_CODE = "REQUEST_CODE";
    }

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        uc.showDialogEvent.postValue(title);
    }

    public void dismissDialog() {
        uc.dismissDialogEvent.postValue(null);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }

    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        params.put(ParameterField.REQUEST_CODE, requestCode);
        uc.startActivityForResultEvent.postValue(params);
    }

    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(clz, null, requestCode);
    }

    public void showShortToast(String content) {
        uc.showShortToastEvent.postValue(content);
    }

    public void showShortToast(int resId) {
        uc.showShortToastEvent.postValue(MyApplication.getInstance().getResources().getText(resId).toString());
    }

    protected void showLoading() {
        uc.onStatusFunction.postValue(StatusFunction.STATE_SHOW_LOADING);
    }

    protected void showError(String msg) {
        StatusFunction statusFunction = StatusFunction.STATE_SHOW_EMPTY;
        statusFunction.setValue(msg);
        uc.onStatusFunction.postValue(statusFunction);
    }

    protected void showEmpty(String msg) {
        StatusFunction statusFunction = StatusFunction.STATE_SHOW_EMPTY;
        statusFunction.setValue(msg);
        uc.onStatusFunction.postValue(statusFunction);
    }

    protected void showEmpty() {
        StatusFunction statusFunction = StatusFunction.STATE_SHOW_EMPTY;
        statusFunction.setValue("暂无数据");
        uc.onStatusFunction.postValue(statusFunction);
    }

    protected void hideLoading() {
        uc.onStatusFunction.postValue(StatusFunction.STATE_HIDE_LOADING);
    }

    public final class UIChangeLiveData extends MutableLiveData {
        private MutableLiveData<String> showDialogEvent;
        private MutableLiveData<String> showShortToastEvent;
        private MutableLiveData<Void> dismissDialogEvent;
        private MutableLiveData<Map<String, Object>> startActivityEvent;
        private MutableLiveData<Map<String, Object>> startActivityForResultEvent;
        private MutableLiveData<Void> finishEvent;
        private MutableLiveData<Void> onBackPressedEvent;
        private MutableLiveData<StatusFunction> onStatusFunction;

        public MutableLiveData<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public MutableLiveData<StatusFunction> getStatusFunctionEvent() {
            return onStatusFunction = createLiveData(onStatusFunction);
        }

        public MutableLiveData<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public MutableLiveData<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public MutableLiveData<Map<String, Object>> getStartActivityForResultEvent() {
            return startActivityForResultEvent = createLiveData(startActivityForResultEvent);
        }

        public MutableLiveData<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public MutableLiveData<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        public MutableLiveData<String> getShortToastEvent() {
            return showShortToastEvent = createLiveData(showShortToastEvent);
        }

        private <T> MutableLiveData<T> createLiveData(MutableLiveData<T> liveData) {
            if (liveData == null) {
                liveData = new MutableLiveData<>();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }
}

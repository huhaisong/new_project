package com.library_common.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.library_common.MyApplication;
import com.library_common.util.DeviceIdUtils;
import com.library_common.util.MMKVUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//网络请求头参数设置
public class HeaderIntercept implements Interceptor {
    private String deviceIdString;
    private String version;

    public HeaderIntercept(Context context) {
        deviceIdString = DeviceIdUtils.getDeviceId(context);

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo p1 = manager.getPackageInfo(context.getPackageName(), 0);
            version = p1.versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl url = original.url();
        Request request = original.newBuilder()
                .addHeader("Authorization", getToken())
                .addHeader("Content-Type", "application/x-www-urlencoded")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("deviceId", deviceIdString)
                .addHeader("client-type", "Android")
                .addHeader("version", version)
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }

    private String getToken() {
        return MMKVUtil.getToken();
    }

    private static final String TAG = "HeaderIntercept";
}

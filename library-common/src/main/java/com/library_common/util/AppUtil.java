package com.library_common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;

public class AppUtil {

   /* public static boolean isNeedLogin(Activity activity, int action) {
        String token = MMKVUtil.getToken();
        if (TextUtils.isEmpty(token)) {
            ARouter.getInstance().build(RouterPath.Login.ACTIVITY_LOGIN).withBoolean(Extras.LOGIN_VALI, true).withInt(Extras.LOGIN_ACTION, action).navigation(activity);
            return true;
        }
        return false;
    }

    *//**
     * 用户是否登录
     *
     * @return true已经登陆，false没有登陆
     *//*
    public static boolean isLogin() {
        String token = MMKVUtil.getToken();
        return !TextUtils.isEmpty(token);
    }*/

    public static int getPackageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
}

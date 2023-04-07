package com.library_common.util;

import com.library_common.contast.Extras;
import com.tencent.mmkv.MMKV;

public class MMKVUtil {
    private static MMKV mmkv = MMKV.defaultMMKV();

    public static String getToken() {
        return mmkv.getString(Extras.TOKEN, "");
    }

    public static void setToken(String token) {
        mmkv.encode(Extras.TOKEN, token);
    }

    public static boolean getIsFirstTime() {
        return mmkv.getBoolean(Extras.FIRST_TIME, true);
    }

    public static void setIsFirstTime(boolean isFirstTime) {
        mmkv.encode(Extras.FIRST_TIME, isFirstTime);
    }

    public static boolean getIsRememberPassword() {
        return mmkv.getBoolean(Extras.REMEMBER_PASSWORD, false);
    }

    public static void setIsRememberPassword(boolean isFirstTime) {
        mmkv.encode(Extras.REMEMBER_PASSWORD, isFirstTime);
    }

    public static boolean getIsAutoLogin() {
        return mmkv.getBoolean(Extras.AUTO_LOGIN, false);
    }

    public static void setIsAutoLogin(boolean isFirstTime) {
        mmkv.encode(Extras.AUTO_LOGIN, isFirstTime);
    }



  /*  public static String getLoginPhone() {
        return mmkv.getString(Extras.PHONE, "");
    }

    public static void setLoginPhone(String phone) {
        mmkv.encode(Extras.PHONE, phone);
    }

    public static String getLoginPassword() {
        return mmkv.getString(Extras.PASSWORD, "");
    }

    public static void setLoginPassword(String phone) {
        mmkv.encode(Extras.PASSWORD, phone);
    }

    public static UserInfoEntity getUserInfo() {
        UserInfoEntity loginResult;
        String json = mmkv.getString(Extras.LOGIN_RESULT, "");
        if (TextUtils.isEmpty(json)) {
            loginResult = new UserInfoEntity();
        } else {
            loginResult = GsonUtil.fromJson(json, UserInfoEntity.class);
        }
        if (loginResult == null)
            loginResult = new UserInfoEntity();
        return loginResult;
    }

    public static void setUserInfo(UserInfoEntity loginResult) {
        if (loginResult == null) {
            mmkv.removeValueForKey(Extras.LOGIN_RESULT);
            return;
        }
        mmkv.encode(Extras.LOGIN_RESULT, GsonUtil.toJson(loginResult));
    }*/
}
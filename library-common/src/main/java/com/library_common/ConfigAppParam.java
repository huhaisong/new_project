package com.library_common;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.library_common.base.BaseApplication;

import java.io.File;

public class ConfigAppParam {
//    public static String Server_Text_Url = "http://110.10.4.144:8082/";//10.10.4.136:8082
      public static String Server_Base_Url = "http://www.xabg0.com/";//http://www.xabg0.com/

    //语法地址1
    public static String Server_Preparation_Url1 = "www.xabg0.com";


    /**
     * *******************官方网站*************************
     * 跳转失败
     * 产品宣传 http://xiaoai.com/
     */
    public final static String Service_Official_website = "http://xiaoai.com/";//

    public final static String UM_appkey = "5d45355b570df3e26d0000e5";

    public static String P2P_TOKEN = "HhkFCs2WR";

    /**
     * 标签
     */
    public static class msgTypeFalge {
        public final static String onLoadMore = "onLoadMore";
        public final static String onRefresh = "onRefresh";
    }

    public static class Handler_Msg {
        public static final int msg_success = 1;
        public static final int msg_failure = 2;
        public static final int msg_start = 3;
        public static final int msg_end = 4;
    }

    //视频保存路径
//    public static String videoDirPath = StorageUtils.getCacheDirectory(BaseApplication.getmContext()).getPath() + "/m3u8Downloader";

    //各种视频地址
    public static class Video_Type {
        //精选视频
        public static final String featured = "featured";
        //最热视频
        public static final String hottest = "hottest";
        //最新视频
        public static final String latest = "latest";
        //搜索视频
        public static final String search = "search";
        //猜你喜欢视频
        public static final String likes = "u-likes";
        //自选频道视频
        public static final String channeloptional = "channel-video";
        //专题
        public static final String topic = "find-by-subject";

    }

    public static class MessageInfo {
        public final static int msg_Refresh_1 = 1;
        public final static int msg_Refresh_2 = 2;
        public final static int msg_loadingMore_3 = 3;
        public final static int msg_loadingMore_4 = 4;
    }

    public static class MMKV {
        public final static String phone_Sigin = "phone_Sigin";//唯一标识符
        public final static String user_name = "user_name";//账号
        public final static String address_ip = "address_ip";//唯一标识符
    }

    //下载路径
    public static final String APK_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/UpdateAPK/xiaoai.apk";

    public static String versionName;

    public static String getVersionName() {
        return versionName;
    }

    public static void setVersionName(String versionName) {
        ConfigAppParam.versionName = versionName;
    }

    /**
     * 获取ip
     */
    public static String getIpString() {
        return Server_Base_Url;
    }

    public static String getIpString(String server_Base_Url) {
        String ip;
        if (server_Base_Url.contains("http")) {
            ip = "" + server_Base_Url;
        } else {
            ip = "http://" + server_Base_Url;
        }
        Server_Base_Url=ip;
        return ip;
    }

    public static String DOWNLOAD_PARENT_PATH = getParentFile(BaseApplication.getInstance()).getPath();

    public static File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }
}

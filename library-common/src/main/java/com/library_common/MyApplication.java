package com.library_common;

import android.app.Application;


import com.alibaba.android.arouter.launcher.ARouter;
import com.library_common.base.BaseApplication;
import com.library_common.http.HeaderIntercept;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.ssl.HttpsUtils;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        MMKV.initialize(this);

        initBugly();

        initRxHttp();

        initARouter();

        initAutoSize();

    }


    private void initARouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }

    //rxHttp
    private void initRxHttp() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
                .addInterceptor(new HeaderIntercept(this))
                .build();

        RxHttpPlugins.init(client)
                .setDebug(true);
        //    .setDebug(boolean)                //是否开启调试模式，开启后，logcat过滤RxHttp，即可看到整个请求流程日志
        //    .setCache(File, long, CacheMode)  //配置缓存目录，最大size及缓存模式
        //    .setExcludeCacheKeys(String...)   //设置一些key，不参与cacheKey的组拼
        //    .setResultDecoder(Function)       //设置数据解密/解码器，非必须
        //    .setConverter(IConverter)         //设置全局的转换器，非必须
        //    .setOnParamAssembly(Function);    //设置公共参数/请求头回调
    }

    private void initAutoSize() {
        //今日头条适配
        AutoSizeConfig.getInstance().setCustomFragment(true);
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.NONE);
    }


    private void initBugly() {
//        CrashReport.initCrashReport(getApplicationContext(), "c0b1e85def", false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    /* private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    *//**
     * 初始化dao
     *//*
    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(sInstance, "caixin.db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }*/

}

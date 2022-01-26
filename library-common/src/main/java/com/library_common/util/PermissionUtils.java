package com.library_common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * 权限申请  工具类
 */
public class PermissionUtils {
    //相机 权限
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    //文件存储权限
    public static final String PERMISSION_STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};

    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;


    /**
     * 检查是否有某个权限
     *
     * @param ctx
     * @param permission
     * @return
     */
    public static boolean checkSelfPermission(Context ctx, String permission) {
        return ContextCompat.checkSelfPermission(ctx.getApplicationContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 动态申请多个权限
     *
     * @param activity
     * @param code
     */
    public static void requestPermissions(Activity activity, @NonNull String[] permissions, int code) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }


    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings(Context context) {
        Context applicationContext = context.getApplicationContext();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + applicationContext.getPackageName()));
        if (!isIntentAvailable(context, intent)) return;
        applicationContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private static boolean isIntentAvailable(Context context, final Intent intent) {
        return context.getApplicationContext()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    public static void checkAndRequestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> lackedPermission = new ArrayList<String>();
//            if (!(activity.checkSelfPermission(PERMISSION_CAMERA)== PackageManager.PERMISSION_GRANTED)) {
//                lackedPermission.add(PERMISSION_CAMERA);
//            }
            if (!(activity.checkSelfPermission(PERMISSION_STORAGE_WRITE) == PackageManager.PERMISSION_GRANTED)) {
                lackedPermission.add(PERMISSION_STORAGE_WRITE);
            }
            // 权限都已经有了，那么直接调用SDK
            if (lackedPermission.size() == 0) {
            } else {
                // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
                String[] requestPermissions = new String[lackedPermission.size()];
                lackedPermission.toArray(requestPermissions);
                activity.requestPermissions(requestPermissions, 101);
            }
        }
    }

}

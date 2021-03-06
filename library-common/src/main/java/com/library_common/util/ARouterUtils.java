package com.library_common.util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.library_common.router.RouterPath;

public class ARouterUtils {
    public static void toExampleActivity() {
        ARouter.getInstance().build(RouterPath.Example.ACTIVITY_EXAMPLE_MAIN).navigation();
    }

    public static void toMAINActivity() {
        ARouter.getInstance().build(RouterPath.Main.ACTIVITY_MAIN_ACTIVITY).navigation();
    }

    public static void toLoginActivity() {
        ARouter.getInstance().build(RouterPath.Login.ACTIVITY_LOGIN).navigation();
    }

    public static void toChangePasswordActivity() {
        ARouter.getInstance().build(RouterPath.Login.ACTIVITY_CHANGE_PASSWORD).navigation();
    }

    public static void toRegisterActivity() {
        ARouter.getInstance().build(RouterPath.Login.ACTIVITY_REGISTER).navigation();
    } public static void toVerifyIDActivity() {
        ARouter.getInstance().build(RouterPath.User.ACTIVITY_VERIFY_ID).navigation();
    }
}

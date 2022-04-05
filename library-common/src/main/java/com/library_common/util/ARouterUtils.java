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
}

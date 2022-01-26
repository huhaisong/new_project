package com.library_common.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterPath {

    /**
     * 主业务组件
     */
    public static class Base {
        private static final String BASE = "/base";
        /*主业务界面*/
        public static final String ACTIVITY_IMAGE_DETAIL = BASE + "/ImageDetail";
        public static final String FRAGMENT_IMAGE_DETAIL = BASE + "/imageDetail";
    }

    public static class Login {
        private static final String LOGIN = "/login";
        /*登录界面*/
        public static final String ACTIVITY_LOGIN = LOGIN + "/Login";
        public static final String ACTIVITY_REGISTER = LOGIN + "/Register";
        public static final String ACTIVITY_RESET_PASSWORD = LOGIN + "/Reset_password";
    }

    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*用户详情*/
        public static final String FRAGMENT_MY_TASK = USER + "/myTask";
        public static final String FRAGMENT_MY_TASK_IMAGE = USER + "/myTaskImage";
        public static final String FRAGMENT_MY_TASK_LINK = USER + "/myTaskLink";
        public static final String FRAGMENT_MY_TASK_VIDEO = USER + "/myTaskVideo";
        public static final String ACTIVITY_USERINFO = USER + "/UserInfo";
        public static final String ACTIVITY_WITHDRAW_RECODE = USER + "/WithdrawRecode";
        public static final String ACTIVITY_BIND_ALIPAY = USER + "/BindAlipay";
        public static final String ACTIVITY_BIND_INVITE_CODE = USER + "/BindInviteCode";
        public static final String ACTIVITY_BIND_PHONE = USER + "/BindPhone";
        public static final String ACTIVITY_SETTING = USER + "/Setting";
        public static final String ACTIVITY_CHANGE_PASSWORD = USER + "/ChangePassword";
        public static final String ACTIVITY_MY_TASK = USER + "/MyTask";
        public static final String ACTIVITY_WITHDRAW_CASH = USER + "/WithdrawCash";
        public static final String ACTIVITY_CHAT = USER + "/Chat";
        public static final String ACTIVITY_UPDATE_IMAGE = USER + "/UpdateImage";
        public static final String ACTIVITY_MONEY_RECODE = USER + "/MoneyRecode";
        public static final String ACTIVITY_MY_TEAM = USER + "/MyTeam";
        public static final String ACTIVITY_ABOUT_US = USER + "/AboutUs";
        public static final String ACTIVITY_MAIN = USER + "/Main";
    }

    public static class Example {
        private static final String EXAMPLE = "/my_example";
        public static final String ACTIVITY_EXAMPLE_MAIN = EXAMPLE + "/Main";
    }
}

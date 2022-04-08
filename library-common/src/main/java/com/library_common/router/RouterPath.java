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
        public static final String ACTIVITY_LOGIN = LOGIN + "/Login";
        public static final String ACTIVITY_REGISTER = LOGIN + "/Register";
        public static final String ACTIVITY_RESET_PASSWORD = LOGIN + "/Reset_password";
        public static final String ACTIVITY_CHANGE_PASSWORD = LOGIN + "/ACTIVITY_CHANGE_PASSWORD";
    }

    public static class User {
        private static final String USER = "/user";
        public static final String ACTIVITY_MAIN = USER + "/Main";
        public static final String FRAGMENT_MINE = USER + "/fragment_mine";
        public static final String ACTIVITY_VERIFY_ID = USER + "/ACTIVITY_VERIFY_ID";
    }

    public static class Home {
        private static final String HOME = "/home";
        public static final String FRAGMENT_HOME = HOME + "/fragment_home";
    }

    public static class Task {
        private static final String TASK = "/task";
        public static final String FRAGMENT_TASK = TASK + "/fragment_task";
    }

    public static class Message {
        private static final String MESSAGE = "/message";
        public static final String FRAGMENT_MESSAGE = MESSAGE + "/fragment_message";
    }

    public static class Example {
        private static final String EXAMPLE = "/my_example";
        public static final String ACTIVITY_EXAMPLE_MAIN = EXAMPLE + "/Main";
    }

    public static class Main {
        private static final String MAIN = "/main";
        public static final String ACTIVITY_MAIN_ACTIVITY = MAIN + "/MainActivity";
    }
}

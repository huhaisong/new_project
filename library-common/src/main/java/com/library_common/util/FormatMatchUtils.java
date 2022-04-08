package com.library_common.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatMatchUtils {
    public static boolean isMobile(String phoneStr, Context context) {
        boolean b = false;
        try {
            int length = regexArray.length;
            for (int i = 0; i < length; i++) {
                b = matches(phoneStr, context, regexArray[i]);
                if (b) {
                    System.err.println("###########test####################phoneNumber---matches--" + phoneStr);
                    return b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    private static boolean matches(String phoneStr, Context context, String regex) {
        boolean b = false;
        try {
            Pattern p = null;
            Matcher m = null;
            if (!TextUtils.isEmpty(phoneStr)) {
                p = Pattern.compile(regex);
                m = p.matcher(phoneStr);
                b = m.matches();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    private static final String[] regexArray = {
            "^(\\+?213|0)(5|6|7)\\d{8}$",
            "^(!?(\\+?963)|0)?9\\d{8}$",
            "^(!?(\\+?966)|0)?5\\d{8}$",
            "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$",
            "^(\\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$",
            "^(\\+?49[ \\.\\-])?([\\(]{1}[0-9]{1,6}[\\)])?([0-9 \\.\\-\\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$",
            "^(\\+?45)?(\\d{8})$",
            "^(\\+?30)?(69\\d{8})$",
            "^(\\+?61|0)4\\d{8}$",
            "^(\\+?44|0)7\\d{9}$",
            "^(\\+?852\\-?)?[569]\\d{3}\\-?\\d{4}$",
            "^(\\+?91|0)?[789]\\d{9}$",
            "^(\\+?64|0)2\\d{7,9}$",
            "^(\\+?27|0)\\d{9}$",
            "^(\\+?26)?09[567]\\d{7}$",
            "^(\\+?34)?(6\\d{1}|7[1234])\\d{7}$",
            "^(\\+?358|0)\\s?(4(0|1|2|4|5)?|50)\\s?(\\d\\s?){4,8}\\d$",
            "^(\\+?33|0)[67]\\d{8}$",
            "^(\\+972|0)([23489]|5[0248]|77)[1-9]\\d{6}$",
            "^(\\+?36)(20|30|70)\\d{7}$",
            "^(\\+?39)?\\s?3\\d{2} ?\\d{6,7}$",
            "^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}$",
            "^(\\+?6?01){1}(([145]{1}(\\-|\\s)?\\d{7,8})|([236789]{1}(\\s|\\-)?\\d{7}))$",
            "^(\\+?47)?[49]\\d{7}$",
            "^(\\+?32|0)4?\\d{8}$",
            "^(\\+?47)?[49]\\d{7}$",
            "^(\\+?48)? ?[5-8]\\d ?\\d{3} ?\\d{2} ?\\d{2}$",
            "^(\\+?55|0)\\-?[1-9]{2}\\-?[2-9]{1}\\d{3,4}\\-?\\d{4}$",
            "^(\\+?351)?9[1236]\\d{7}$",
            "^(\\+?7|8)?9\\d{9}$",
            "^(\\+3816|06)[- \\d]{5,9}$",
            "^(\\+?90|0)?5\\d{9}$",
            "^(\\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$",
            "^(\\+?0?86\\-?)?1[345789]\\d{9}$",
            "^(\\+?886\\-?|0)?9\\d{8}$"
    };

    //校验通过返回true，否则返回false
    public static boolean isMobile(String mobile) {

        String str = mobile;
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        return m.matches();
    }


    /**
     * 是否是邮箱
     *
     * @return
     */
    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();
        return isMatched;
    }
}

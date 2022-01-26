package com.library_common.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static final String COMMON_TIME_TYPE = "yyyy-MM-dd HH:mm:ss";
    public static final String COMMON_TIME_TYPE_1 = "yyyy.MM.dd HH:mm:ss";
    public static final String COMMON_TIME_TYPE_2 = "HH:mm";


    public static String getHourMath(long seconds) {
        String time = "";
        int hour = (int) (seconds / 3600);
        if (hour != 0) {
            if (hour < 10) {
                time = "0" + hour;
            } else {
                time = hour + "";
            }
            seconds = seconds - (hour * 3600);
        } else {
            time = "00";
        }
        int min = (int) (seconds / 60);
        if (min != 0) {
            if (min < 10) {
                time = time + ":0" + min;
            } else {
                time = time + ":" + min;
            }
            seconds = seconds - (min * 60);
        } else {
            time = time + ":00";
        }

        if (seconds < 10) {
            time = time + ":0" + seconds;
        } else {
            time = time + ":" + seconds;
        }

        return time;
    }

    public static String splitTime(String createTime) {
        String[] arr = createTime.split(" ");
        if (arr.length > 0) {
            return arr[0];
        }
        return createTime;
    }

    public static String getStringDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_TYPE);
        return sdf.format(new Date(time));
    }

    public static String getStringDate1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_TYPE_1);
        return sdf.format(new Date(time));
    }

    public static long getTimeOfLong(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(COMMON_TIME_TYPE);
        Date date = new Date();
        long mathTime = 0L;
        try {
            date = dateFormat.parse(time);
            mathTime = date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return mathTime;
    }


    public static String changeFormat(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(COMMON_TIME_TYPE);
        Date date = new Date();
        SimpleDateFormat newDateFormat = new SimpleDateFormat(COMMON_TIME_TYPE_2);
        long mathTime = 0L;
        try {
            date = dateFormat.parse(time);
            mathTime = date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newDateFormat.format(new Date(mathTime));
    }

    public static String getCurrentType() {
        return getMatchTime(getStringDate(getCurTimeLong()));
    }

    public static String getMatchTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return time;
        }
        long longTime = getTimeOfLong(time);
        if (longTime == 0) {
            return "刚刚";
        }
        long currentTime = TimeUtils.getCurTimeLong();
        int mathTime = (int) ((currentTime - longTime) / 1000);
        if (mathTime <= 60) {
            return "刚刚";
        } else if (mathTime <= 3600) {
            return (mathTime / 60) + "分钟前";
        } else if (mathTime <= 24 * 3600) {
            return "昨天" + getHourAndMinToString(longTime);
        } else if (mathTime <= 48 * 3600) {
            return getDateToString(longTime);
        } else if (mathTime <= 365 * 24 * 3600) {
            return getDateToString2(longTime);
        }
        return time;
    }

    public static String getHourAndMinToString(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static String getDateToString(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        return format.format(date);
    }

    public static String getDateToString1(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(date);
    }

    public static String getDateToString2(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static long getCurTimeLong() {
        return System.currentTimeMillis();
    }
}

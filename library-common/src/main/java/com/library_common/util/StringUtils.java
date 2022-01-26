package com.library_common.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StringUtils {

    public static String defString(String s) {
        if (null == s) {
            s = "";
        }
        return s;
    }

    public static String defString0(String s) {
        if (null == s) {
            s = "0";
        }
        return s;
    }

    public static String getRaw(Context context, int id) {
        InputStream stream = context.getResources().openRawResource(id);
        return read(stream);
    }

    public static String read(InputStream stream) {
        return read(stream, "utf-8");
    }

    public static String read(InputStream is, String encode) {
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getBase64(String base64Id) {
        String result = "";
        if (!TextUtils.isEmpty(base64Id)) {
            if (!TextUtils.isEmpty(base64Id)) {
                result = new String(Base64.decode(base64Id.getBytes(), Base64.DEFAULT));
            }
        }
        return result;
    }

    public static String mathMoney(String balance) {
        String money = "0";
        try {
            double balance1 = Double.parseDouble(balance);
            if (balance1 < 1000) {
                money = balance;
            } else if (balance1 / 1000 < 10) {
                BigDecimal b = new BigDecimal(balance1 / 1000);
                money = b.setScale(1, BigDecimal.ROUND_DOWN) + "k";
            } else {
                BigDecimal b = new BigDecimal(balance1 / 10000);
                money = b.setScale(1, BigDecimal.ROUND_DOWN) + "w";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return money;
    }


    public static String getMoney(int money) {
        float price = ((float) money) / 100f;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(price);//format 返回的是字符串
    }
}

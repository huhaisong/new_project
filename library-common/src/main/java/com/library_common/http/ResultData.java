package com.library_common.http;

public class ResultData<T> {
    public static final int ERROR_CODE = -1001;
    private T data;
    private int code = -1;
    private String msg;
    //返回状态默认成功
    public boolean isSuccess = true;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        isSuccess = (code == -1 && data != null);
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setErrorMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.isSuccess = false;
    }
}

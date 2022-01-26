package com.library_common.widget;

public enum StatusFunction {
    STATE_SHOW_LOADING(""), STATE_SHOW_ERROR(""), STATE_SHOW_EMPTY(""), STATE_HIDE_LOADING("");
    private String value;

    StatusFunction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

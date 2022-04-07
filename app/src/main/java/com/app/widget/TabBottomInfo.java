package com.app.widget;

import android.graphics.drawable.Drawable;

public class TabBottomInfo {

    private String name;

    private int iconDrawableResource;

    private boolean isCenter;

    public TabBottomInfo(String name, int iconDrawable, boolean isCenter) {
        this.name = name;
        this.iconDrawableResource = iconDrawable;
        this.isCenter = isCenter;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void setCenter(boolean center) {
        isCenter = center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconDrawable() {
        return iconDrawableResource;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawableResource = iconDrawable;
    }
}
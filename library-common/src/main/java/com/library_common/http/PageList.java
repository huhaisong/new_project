package com.library_common.http;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
public class PageList<T> {

    private int     curPage; //当前页数
    private int     pageCount; //总页数
    private int     total; //总条数
    private List<T> data;

    public int getCurPage() {
        return curPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(ArrayList<T> datas) {
        this.data = datas;
    }
}


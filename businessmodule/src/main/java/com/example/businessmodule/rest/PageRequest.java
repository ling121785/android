package com.example.businessmodule.rest;

/**
 * Created by root on 2016/10/21.
 */
public class PageRequest {
    private int curPage=0;
    private int pageSize=RestApiUrl.DEFAULT_LIST_SIZE;

    public PageRequest() {
    }
    public PageRequest(int curPage, int pageSize) {
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    public PageRequest(int curPage) {
        this.curPage = curPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

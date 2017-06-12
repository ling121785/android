package com.example.businessmodule.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 列表响应基类
 */
public class BaseListResponse<T>{
    @SerializedName("page")
    private Page page;

    @SerializedName("data")
    private List<T> dataList;

    public BaseListResponse() {
    }

    public BaseListResponse(List<T> dataList,int curPage,int pageSize,int totleSize) {
        this.dataList = dataList;
        this.page = new Page();
        page.setSize(pageSize);
        page.setPageNumber(curPage);
        page.setTotalElements(totleSize);
        page.setSize(dataList==null?0:dataList.size());
        page.setTotalPages((int)Math.ceil(totleSize*1.0/pageSize));
    }

    public int getTotal() {
        return getPage().getTotalElements();
    }

    public void setTotal(int total) {
        getPage().setTotalElements(total);
    }

    public int getCount() {
        return getPage().getSize();
    }

    public void setCount(int count) {
        getPage().size = count;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Page getPage() {
        if(page==null)
            page=new Page();
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static class Page{
        @SerializedName("size")
        private int size;//当前页数量
        @SerializedName("number")
        private int pageNumber;//当前在第几页
        @SerializedName("totalPages")
        private int totalPages;//总页数
        @SerializedName("totalElements")
        private int totalElements;//所有数据总数

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean hasMore(){
            return pageNumber < totalPages - 1;
        }
    }

}



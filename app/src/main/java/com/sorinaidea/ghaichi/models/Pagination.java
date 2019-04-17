package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pagination<DataModel> {

    @SerializedName("first_page_url")
    private
    String firstPageUrl;
    @SerializedName("last_page_url")
    private
    String lastPageUrl;

    @SerializedName("next_page_url")
    private
    String nextPageUrl;
    @SerializedName("prev_page_url")
    private
    String prevPageUrl;

    @SerializedName("path")
    private
    String path;

    @SerializedName("from")
    private
    int from;
    @SerializedName("last_page")
    private
    int lastPage;
    @SerializedName("per_page")
    private
    int perPage;
    @SerializedName("to")
    private
    int to;
    @SerializedName("total")
    private
    int total;
    @SerializedName("current_page")
    private
    int currentPage;

    @SerializedName("data")
    private
    List<DataModel> data;


    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl) {
        this.lastPageUrl = lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }
}


/*

    "current_page": 1,
    "data": [
        {
            // Object data
        },
        {
            // Object data
        },
        .
        .
        .
    ],
    "first_page_url": "http://ghaichi.local/api/app/newest?page=1",
    "from": 1,
    "last_page": 1,
    "last_page_url": "http://ghaichi.local/api/app/newest?page=1",
    "next_page_url": null,
    "path": "http://ghaichi.local/api/app/newest",
    "per_page": 5,
    "prev_page_url": null,
    "to": n,
    "total": 3

*/
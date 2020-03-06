package com.frankdevhub.site.core.data.rest.results;

import com.github.pagehelper.PageInfo;


public class PageResult<T> extends Result {
    private PageInfo<T> data;

    public PageInfo<T> getData() {
        return this.data;
    }

    public PageResult<T> setData(PageInfo<T> data) {
        this.data = data;
        return this;
    }
}

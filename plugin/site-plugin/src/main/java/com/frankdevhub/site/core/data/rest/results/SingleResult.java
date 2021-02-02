package com.frankdevhub.site.core.data.rest.results;

@SuppressWarnings("all")
public class SingleResult<T> extends Result {
    private T data;

    public T getData() {
        return this.data;
    }

    public SingleResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}

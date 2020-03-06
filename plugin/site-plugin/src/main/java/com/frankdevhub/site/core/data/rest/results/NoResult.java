package com.frankdevhub.site.core.data.rest.results;


public class NoResult extends Result {
    private Boolean result;

    public Boolean getResult() {
        return this.result;
    }

    public NoResult setResult(Boolean result) {
        this.result = result;
        return this;
    }
}

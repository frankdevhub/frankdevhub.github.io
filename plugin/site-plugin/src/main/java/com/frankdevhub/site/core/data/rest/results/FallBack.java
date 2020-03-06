package com.frankdevhub.site.core.data.rest.results;


public class FallBack<T> {
    private static final String ERR_CODE = "406";
    private static final String ERR_MSG = "invoke remote service failure";

    public NoResult getNoResult() {
        NoResult result = new NoResult();
        result.setMessage(ERR_MSG);
        result.setResult(Boolean.valueOf(false));
        result.setStatus("406");
        return result;
    }

    public PageResult<T> getPageResult() {
        PageResult result = new PageResult();
        result.setMessage(ERR_MSG);
        result.setStatus("406");
        return result;
    }

    public SingleResult<T> getSingleResult() {
        SingleResult result = new SingleResult();
        result.setMessage(ERR_MSG);
        result.setStatus("406");
        return result;
    }
}

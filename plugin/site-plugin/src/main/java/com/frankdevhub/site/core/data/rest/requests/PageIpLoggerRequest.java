package com.frankdevhub.site.core.data.rest.requests;

import javax.validation.constraints.NotNull;

public class PageIpLoggerRequest {

    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }
}

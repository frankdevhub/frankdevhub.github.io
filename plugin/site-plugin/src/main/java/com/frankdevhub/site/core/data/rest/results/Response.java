package com.frankdevhub.site.core.data.rest.results;

import com.frankdevhub.site.core.exception.*;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("all")
public class Response<T> {
    public static Integer BASE_RESULT_UNHANDLED_ERROR = -2;
    public static Integer BASE_RESULT_STATUS_SUCCESS = 1;
    public static Integer BASE_RESULT_STATUS_ERROR = 0;

    private String msg;
    private T info;
    private String status;

    public String getStatus() {
        return this.status;
    }

    public Response<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public T getData() {
        return this.info;
    }

    public Response<T> setData(T data) {
        this.info = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Boolean isSuccess() {
        if (StringUtils.isEmpty(getStatus()))
            return Boolean.FALSE;
        if (getStatus().startsWith("1"))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public Boolean isFailed() {
        return Boolean.valueOf(!(isSuccess().booleanValue()));
    }

    public Response<T> success() {
        setStatus(Integer.toString(BASE_RESULT_STATUS_SUCCESS));
        return this;
    }

    public Response<T> failed(Exception e) {
        if (e instanceof BusinessException || e instanceof ArgumentIsInvalidException ||
                e instanceof ArgumentIsNullException || e instanceof NoSuchPermissionException
                || e instanceof InvokeRemoteException || e instanceof PlatformException || e instanceof RuntimeException) {
            setStatus(Integer.toString(BASE_RESULT_STATUS_ERROR));
        } else {
            setStatus(Integer.toString(BASE_RESULT_UNHANDLED_ERROR));
        }

        return this;
    }

}

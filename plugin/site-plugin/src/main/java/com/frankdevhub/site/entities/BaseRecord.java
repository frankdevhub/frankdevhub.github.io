package com.frankdevhub.site.entities;

import javax.persistence.Column;
import java.util.Date;

public class BaseRecord<T> {

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    public Long getCreateTime() {
        return createTime;
    }

    private BaseRecord setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    private BaseRecord setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public T doCreateEntity() {
        Long timeStamp = new Date().getTime();
        this.setCreateTime(timeStamp).setUpdateTime(timeStamp);
        return (T) this;
    }

    public T doUpdateEntity() {
        this.setUpdateTime(new Date().getTime());
        return (T) this;
    }
}

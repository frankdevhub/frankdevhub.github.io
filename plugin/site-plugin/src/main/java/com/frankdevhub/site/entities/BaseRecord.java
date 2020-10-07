package com.frankdevhub.site.entities;

import javax.persistence.Column;
import java.util.Date;

public class BaseRecord<T> {

	@Column(name = "create_time")
	private Long createTime;

	@Column(name = "update_time")
	private Long updateTime;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_date")
	private Date updateDate;

	public Long getCreateTime() {
		return createTime;
	}

	@SuppressWarnings("rawtypes")
	private BaseRecord setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	@SuppressWarnings("rawtypes")
	private BaseRecord setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	@SuppressWarnings("rawtypes")
	public BaseRecord setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	@SuppressWarnings("rawtypes")
	public BaseRecord setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
		return this;
	}

	@SuppressWarnings("unchecked")
	public T doCreateEntity() {
		Date date = new Date();
		Long timeStamp = date.getTime();
		this.setCreateTime(timeStamp).setUpdateTime(timeStamp);
		this.setCreateDate(date).setUpdateDate(date);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T doUpdateEntity() {
		this.setUpdateTime(new Date().getTime());
		return (T) this;
	}

}

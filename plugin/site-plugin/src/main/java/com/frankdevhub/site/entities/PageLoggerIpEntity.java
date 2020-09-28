package com.frankdevhub.site.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "site_page_log")
public class PageLoggerIpEntity extends BaseRecord<PageLoggerIpEntity> {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "log_id")
	private Long logId;

	@Column(name = "date")
	private Long date;

	@Column(name = "url")
	private String url;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "mac_address")
	private String macAddress;

	@Column(name = "lat")
	private String latitude;

	@Column(name = "lng")
	private String longitude;

	@Column(name = "address")
	private String address;

	public Long getId() {
		return id;
	}

	public PageLoggerIpEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getLogId() {
		return logId;
	}

	public PageLoggerIpEntity setLogId(Long logId) {
		this.logId = logId;
		return this;
	}

	public Long getDate() {
		return date;
	}

	public PageLoggerIpEntity setDate(Long date) {
		this.date = date;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public PageLoggerIpEntity setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public PageLoggerIpEntity setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public PageLoggerIpEntity setMacAddress(String macAddress) {
		this.macAddress = macAddress;
		return this;
	}

	public String getLatitude() {
		return latitude;
	}

	public PageLoggerIpEntity setLatitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public String getLongitude() {
		return longitude;
	}

	public PageLoggerIpEntity setLongitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public PageLoggerIpEntity setAddress(String address) {
		this.address = address;
		return this;
	}
}

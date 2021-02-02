package com.frankdevhub.site.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("all")
@Table(name = "site_page_log")
public class PageLoggerIpRecord extends BaseRecord<PageLoggerIpRecord> {

	@Id
	@Column(name = "id")
	private String id;

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

	public String getId() {
		return id;
	}

	public PageLoggerIpRecord setId(String id) {
		this.id = id;
		return this;
	}

	public Long getDate() {
		return date;
	}

	public PageLoggerIpRecord setDate(Long date) {
		this.date = date;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public PageLoggerIpRecord setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public PageLoggerIpRecord setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public PageLoggerIpRecord setMacAddress(String macAddress) {
		this.macAddress = macAddress;
		return this;
	}

	public String getLatitude() {
		return latitude;
	}

	public PageLoggerIpRecord setLatitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public String getLongitude() {
		return longitude;
	}

	public PageLoggerIpRecord setLongitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public PageLoggerIpRecord setAddress(String address) {
		this.address = address;
		return this;
	}
}

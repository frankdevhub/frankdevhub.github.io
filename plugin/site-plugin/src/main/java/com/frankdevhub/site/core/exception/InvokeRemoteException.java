package com.frankdevhub.site.core.exception;

public class InvokeRemoteException extends PlatformException {

	private String status;

	public InvokeRemoteException(String status, String message) {
		super(message);
		setStatus(status);
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

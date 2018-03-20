package com.bench.app.stockmanage.base.web;

import java.util.Map;

import com.bench.app.stockmanage.base.EnumBase;
import com.bench.app.stockmanage.base.ErrorCode;

/**
 * ´íÎó
 * 
 * @author chenbug
 * 
 * @version $Id: ErrorEntry.java, v 0.1 2014-4-25 ÏÂÎç2:42:42 chenbug Exp $
 */
public class ErrorEntry {

	private String code;

	private String message;

	private Map<String, Object> properties;

	public ErrorEntry() {
		super();
	}

	public ErrorEntry(EnumBase errorEnum) {
		this.code = errorEnum.name();
		this.message = errorEnum.message();
	}

	public ErrorEntry(ErrorCode errorCode) {
		this.code = errorCode.getName();
		this.message = errorCode.getMessage();
	}

	public ErrorEntry(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ErrorEntry(String code, String message, Map<String, Object> properties) {
		super();
		this.code = code;
		this.message = message;
		this.properties = properties;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}

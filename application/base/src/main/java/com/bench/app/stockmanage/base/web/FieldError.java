package com.bench.app.stockmanage.base.web;

import java.util.HashMap;
import java.util.Map;

import com.bench.app.stockmanage.base.EnumBase;

/**
 * ×Ö¶Î´íÎó
 * 
 * @author chenbug
 * 
 * @version $Id: FieldError.java, v 0.1 2014-4-25 ÏÂÎç2:31:38 chenbug Exp $
 */
public class FieldError {

	/**
	 * ×Ö¶Î
	 */
	private String name;

	/**
	 * ´íÎó
	 */
	private ErrorEntry error;

	/**
	 * ÆäËûÊä³öÊôĞÔ
	 */
	private Map<String, Object> outputParameters = new HashMap<String, Object>();

	public FieldError() {
		super();
	}

	public FieldError(String name, EnumBase errorEnum) {
		super();
		this.name = name;
		this.setErrorEnum(errorEnum);
	}

	public FieldError(String name, ErrorEntry error) {
		super();
		this.name = name;
		this.error = error;
	}

	public void setErrorEnum(EnumBase errorEnum) {
		if (this.error == null) {
			this.error = new ErrorEntry();
		}
		error.setCode(errorEnum.name());
		error.setMessage(errorEnum.message());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getOutputParameters() {
		return outputParameters;
	}

	public void setOutputParameters(Map<String, Object> outputParameters) {
		this.outputParameters = outputParameters;
	}

	public ErrorEntry getError() {
		return error;
	}

	public void setError(ErrorEntry error) {
		this.error = error;
	}
}

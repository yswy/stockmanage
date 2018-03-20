/**
 * 
 */
package com.bench.app.stockmanage.base;

import com.bench.app.stockmanage.base.utils.StringUtils;
import com.bench.app.stockmanage.base.utils.ToStringObject;

/**
 * <p>
 * 
 * </p>
 * 
 * @author chenbug
 * @version $Id: ErrorCode.java,v 0.1 2009-8-24 ÉÏÎç11:04:35 chenbug Exp $
 */

public class ErrorCode extends ToStringObject implements EnumBase {

	private String name;

	private String message;

	private int value;

	public ErrorCode() {
		super();
	}

	public boolean nameEquals(EnumBase enumBase) {
		return StringUtils.equals(this.name, enumBase.name());
	}

	public ErrorCode(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

	public ErrorCode(EnumBase enumBase) {
		super();
		this.name = enumBase.name();
		this.message = enumBase.message();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#message()
	 */
	public String message() {
		// TODO Auto-generated method stub
		return this.message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#name()
	 */
	public String name() {
		// TODO Auto-generated method stub
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#value()
	 */
	public Number value() {
		// TODO Auto-generated method stub
		return this.value;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}

}

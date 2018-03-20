/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: ReflectionToStringBuilder.java, v 0.1 2013-12-16 下午4:03:18
 *          chenbug Exp $
 */
public class ReflectionToStringBuilder extends org.apache.commons.lang.builder.ReflectionToStringBuilder {

	private static final Class<?>[] IGNORE_CLASS = new Class<?>[] {};

	/**
	 * 隐藏的字段类型
	 */
	private String[] maskFieldNames;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * If the style is <code>null</code>, the default style is used.
	 * </p>
	 * 
	 * @param object
	 *            the Object to build a <code>toString</code> for, must not be
	 *            <code>null</code>
	 * @param style
	 *            the style of the <code>toString</code> to create, may be
	 *            <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the Object passed in is <code>null</code>
	 */
	public ReflectionToStringBuilder(Object object, ToStringStyle style) {
		super(object, style);
	}

	@Override
	protected boolean accept(Field field) {
		// TODO Auto-generated method stub
		boolean accept = super.accept(field);
		if (!accept) {
			return false;
		}
		if (ArrayUtils.contains(IGNORE_CLASS, field.getType())) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getValue(Field field) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		// 如果是字节输出，输出字节长度
		if (field.getType().equals(byte[].class)) {
			return "'byte[] data,ignore output by bench,length=" + ArrayUtils.getLength(super.getValue(field)) + "'";
		}
		Object value = super.getValue(field);
		if (value == null) {
			return value;
		}
		if (ArrayUtils.contains(this.maskFieldNames, field.getName())) {
			return StringMaskUtils.maskAuto(ObjectUtils.toString(value));
		}
		return value;
	}

	public String[] getMaskFieldNames() {
		return maskFieldNames;
	}

	public void setMaskFieldNames(String[] maskFieldNames) {
		this.maskFieldNames = maskFieldNames;
	}
}

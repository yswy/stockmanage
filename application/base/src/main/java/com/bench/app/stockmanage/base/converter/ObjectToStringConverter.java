/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.converter;

import org.apache.commons.lang.ObjectUtils;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: ObjectToStringConverter.java, v 0.1 2013-1-17 обнГ2:35:55 chenbug
 *          Exp $
 */
public class ObjectToStringConverter<T> implements ToStringConverter<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.lang.ToStringConverter#convert(java.lang.Object)
	 */
	@Override
	public String convert(T t) {
		// TODO Auto-generated method stub
		return ObjectUtils.toString(t);
	}

}

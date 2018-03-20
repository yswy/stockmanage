package com.bench.app.stockmanage.base.utils;

/**
 * 转换成字符串对象
 * 
 * @author chenbug
 * 
 * @version $Id: ToStringObject.java, v 0.1 2014-9-9 下午6:49:05 chenbug Exp $
 */
public class ToStringObject {

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

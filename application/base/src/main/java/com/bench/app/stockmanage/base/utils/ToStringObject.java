package com.bench.app.stockmanage.base.utils;

/**
 * ת�����ַ�������
 * 
 * @author chenbug
 * 
 * @version $Id: ToStringObject.java, v 0.1 2014-9-9 ����6:49:05 chenbug Exp $
 */
public class ToStringObject {

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

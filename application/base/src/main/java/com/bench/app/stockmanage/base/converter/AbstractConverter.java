/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.converter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * ���󹫹�ת����
 * @author zuoer
 *
 * @version $Id: AbstractConverter.java, v 0.1 2017��10��16�� ����4:10:10 zuoer Exp $
 */
public class AbstractConverter<FromObj,ToObj> {
	
	private static final Logger log=Logger.getLogger(AbstractConverter.class);
	
	/**
	 * ����һ���µĶ���
	 * 
	 * @param fromObj
	 * @return
	 */
	protected ToObj newModel() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<ToObj> clasz = (Class<ToObj>) type.getActualTypeArguments()[1];
		try {
			return (ToObj) clasz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("������ʵ���쳣,clasz=" + clasz, e);
		}
	}
	/**
	 * ����������ʵ�ָ��Ի���ת������
	 * 
	 * @param fromObj
	 * @param toObj
	 */
	protected void convertSpecial(FromObj fromObj, ToObj toObj) {

	}
	

	/**
	 * ת��һ��
	 * @param fromObj
	 * @return
	 */
	public ToObj convertOne(FromObj fromObj){
		if(fromObj==null){
		return null;
		}
		ToObj toObj=newModel();
		ConverterUtils.convert(fromObj, toObj);
		convertSpecial(fromObj, toObj);
		return toObj;
	}
	
	/**
	 * ת�����
	 * 
	 * @param fromObjList
	 * @return
	 */
	public List<ToObj> convertMany(List<FromObj> fromObjList) {
		List<ToObj> returnList = new ArrayList<ToObj>();
		for (FromObj fromObj : fromObjList) {
			returnList.add(this.convertOne(fromObj));
		}
		return returnList;
	}
}

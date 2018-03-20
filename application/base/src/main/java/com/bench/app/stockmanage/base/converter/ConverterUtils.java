/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.converter;

import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.security.util.FieldUtils;

/**
 * ת������
 * @author zuoer
 *
 * @version $Id: ConverterUtils.java, v 0.1 2017��10��16�� ����5:26:58 zuoer Exp $
 */
public class ConverterUtils {
	
	private static final Logger log=Logger.getLogger(ConverterUtils.class);

	public static void convert(Object from,Object to){
		for(Field field:getAllFields(to.getClass())){
			Field fromField;
			try {
				fromField = FieldUtils.getField(from.getClass(), field.getName());
			} catch (IllegalStateException e) {
				log.info("�����쳣,�����������");
				continue;
			}
			if(fromField!=null && fromField.getType().equals(field.getType())){
				try {
					FieldUtils.setProtectedFieldValue(field.getName(), to,  FieldUtils.getFieldValue(from, fromField.getName()));
				} catch (IllegalArgumentException | IllegalAccessException | IllegalStateException e) {
					// TODO Auto-generated catch block
					log.info("�����쳣,�����������");
					continue;
				}
			}
		}
	}
	

	public static Field[] getAllFields(Class<?> clasz){
		if(clasz==null){
			return new Field[]{};
		}
		//�౾�������
		Field[] fieldList=clasz.getDeclaredFields();
				
		//�ɸ���̳�����������,�ݹ�
		return (Field[]) ArrayUtils.addAll(fieldList, getAllFields(clasz.getSuperclass()));
	}
}

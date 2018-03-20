package com.bench.app.stockmanage.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.bench.app.stockmanage.base.spring.annotation.ClassAnno;
import com.bench.app.stockmanage.base.spring.annotation.FieldAnno;
import com.bench.app.stockmanage.base.validate.annotation.ParentValidate;

/**
 * Class��Ӧ��Field������
 * 
 * @author chenbug
 * 
 * @version $Id: FieldUtils.java, v 0.1 2009-8-18 ����06:10:13 chenbug Exp $
 */
public class FieldUtils {

	private static final Logger logger = Logger.getLogger(FieldUtils.class);

	public static final FieldUtils INSTANCE = new FieldUtils();

	private static Field NULL_FIELD = null;
	static {
		try {
			NULL_FIELD = ClassNullField.class.getDeclaredField("NULL_FIELD");
		} catch (Exception e) {
			throw new RuntimeException("��ʼ��ClassUtils�쳣", e);
		}
	}

	private static Map<Class<?>, Map<String, Field>> wthSuperClassEachFieldCacheMap = new ConcurrentHashMap<Class<?>, Map<String, Field>>();

	private static Map<Class<?>, Set<Field>> wthSuperClassAllFieldCacheMap = new ConcurrentHashMap<Class<?>, Set<Field>>();

	public static List<Class<?>> getFieldParameterizedClass(Field field) {
		// �����List���ͣ��õ���Generic������
		Type genericType = field.getGenericType();
		if (genericType == null)
			return null;
		// ����Ƿ��Ͳ���������
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			List<Class<?>> returnList = new ArrayList<Class<?>>();
			for (Type type : pt.getActualTypeArguments()) {
				if (type instanceof Class<?>) {
					returnList.add((Class<?>) type);
				} else if (type instanceof ParameterizedType) {
					ParameterizedType childPt = (ParameterizedType) type;
					if (childPt.getRawType() instanceof Class<?>) {
						returnList.add((Class<?>) childPt.getRawType());
					}

				}
			}
			return returnList;
		}
		return new ArrayList<Class<?>>(0);
	}

	/**
	 * @param field
	 * @return
	 */
	public static Type[] getFieldParameterizedType(Field field) {
		// �����List���ͣ��õ���Generic������
		Type genericType = field.getGenericType();
		if (genericType == null)
			return null;
		// ����Ƿ��Ͳ���������
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			return pt.getActualTypeArguments();
		}
		return null;
	}

	/**
	 * ��ȡclazz��field��ParameterizedType����List�ڵ���������
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Type[] getFieldParameterizedType(Class<?> clazz, String fieldName) {
		Field field = getFieldSafe(clazz, fieldName);
		if (field == null) {
			return null;
		}
		return getFieldParameterizedType(field);
	}

	public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		Map<String, Field> fieldMap = wthSuperClassEachFieldCacheMap.get(clazz);
		if (fieldMap == null) {
			fieldMap = new ConcurrentHashMap<String, Field>();
			wthSuperClassEachFieldCacheMap.put(clazz, fieldMap);
		}
		Field field = fieldMap.get(fieldName);
		if (field != null) {
			if (field == NULL_FIELD) {
				throw new NoSuchFieldException("�޷��ҵ�field��class:" + clazz + "��field��" + fieldName);
			}
			return field;
		}
		try {
			field = clazz.getDeclaredField(fieldName);
			fieldMap.put(fieldName, field);
			return field;
		} catch (NoSuchFieldException e) {
			Class<?> parentClass = clazz.getSuperclass();
			if (parentClass == null) {
				fieldMap.put(fieldName, NULL_FIELD);
				throw new NoSuchFieldException("�޷��ҵ�field��class:" + clazz + "��field��" + fieldName);
			}
			return getField(parentClass, fieldName);
		}
	}

	public static Set<Field> getAllField(Class<?> clazz) {
		Set<Field> fieldSet = wthSuperClassAllFieldCacheMap.get(clazz);
		if (fieldSet != null) {
			return fieldSet;
		}
		fieldSet = new HashSet<Field>();
		CollectionUtils.addAll(fieldSet, clazz.getDeclaredFields());
		for (Class<?> parentClass : ClassUtils.getSuperclasses(clazz)) {
			CollectionUtils.addAll(fieldSet, parentClass.getDeclaredFields());
		}
		return fieldSet;
	}

	/**
	 * �ݹ��ȡField������ڵ�ǰ�����޷��ҵ����򵽸����в��ң�����Ҳ������򷵻�null�����׳��쳣
	 * 
	 * @param clasz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getFieldSafe(Class<?> clasz, String fieldName) {
		try {
			return getField(clasz, fieldName);
		} catch (NoSuchFieldException e) {
			return null;
		}

	}

	/**
	 * ��ȡע��
	 * 
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Annotation getAnnotation(Field field, String annotationClass) {
		try {
			return field.getAnnotation(ClassUtils.forName(annotationClass));
		} catch (ClassNotFoundException e) {
			logger.error("�޷��ҵ�ע����,class=" + annotationClass);
			return null;
		}
	}

	/**
	 * �Ƿ����ע��
	 * 
	 * @param field
	 * @param annotationClass
	 * @return
	 */
	public static boolean containsAnnotation(Field field, String annotationClass) {
		return getAnnotation(field, annotationClass) != null;
	}

	/**
	 * �ݹ��ȡField������ڵ�ǰ�����޷��ҵ����򵽸����в���
	 * 
	 * @param clasz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static List<Field> getAllField(Class<?> clasz, Class<?> fieldType) {
		List<Field> returnList = new ArrayList<Field>();
		for (Field field : getAllField(clasz)) {
			if (field.getType() == fieldType) {
				returnList.add(field);
			}
		}
		return returnList;
	}

	public static boolean hasField(Class<?> clasz, String fieldName) {
		try {
			return getField(clasz, fieldName) != null;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}

	public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
		Field field = null;
		try {
			field = getField(obj.getClass(), fieldName);
		} catch (NoSuchFieldException e) {
			logger.error("�޷��ҵ����ԣ�" + fieldName, e);
			return;
		}
		try {
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception e) {
			logger.error("��������ֵ�쳣��" + fieldName, e);
			return;
		}
	}

	/**
	 * ��������ֵ
	 * 
	 * @param obj
	 * @param field
	 * @param fieldValue
	 */
	public static void setFieldValueSafe(Object obj, Field field, Object fieldValue) {
		try {
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception e) {
			return;
		}
	}

	public static void setStaticFieldValue(Class<?> objClass, String fieldName, Object fieldValue) {
		Field field = null;
		try {
			field = getField(objClass, fieldName);
		} catch (NoSuchFieldException e) {
			logger.error("�޷��ҵ����ԣ�" + fieldName, e);
			return;
		}
		try {
			field.setAccessible(true);
			field.set(null, fieldValue);
		} catch (Exception e) {
			logger.error("��������ֵ�쳣��" + fieldName, e);
			return;
		}
	}

	/**
	 * �õ�field�ı���
	 * 
	 * @param clasz
	 * @param fieldName
	 * @return
	 */
	public static String getFieldAlias(Class<?> clasz, String fieldName) {
		Field field = null;
		try {
			field = clasz.getDeclaredField(fieldName);
		} catch (Exception e) {
			return null;
		}
		if (field == null)
			return null;

		return getFieldAlias(field);

	}

	public static Annotation[] getFieldValidateAnnotation(Class<?> clasz, String fieldName) {
		Field field = null;
		try {
			field = clasz.getDeclaredField(fieldName);
		} catch (Exception e) {
			return null;
		}
		if (field == null)
			return null;

		return getFieldValidateAnnotation(field);
	}

	public static <T> T getFieldValidateAnnotation(Field field, Class<T> annotationClass) {
		Annotation t = field.getAnnotation((Class) annotationClass);
		if (t != null) {
			return (T) t;
		}

		/**
		 * ���Ҹ���͸���
		 */
		Class<?> currentClass = field.getDeclaringClass();
		Field parentField = null;
		while (parentField == null || parentField.getAnnotation((Class) annotationClass) == null) {
			parentField = null;
			Class<?> parentClass = null;
			ClassAnno classAlias = currentClass.getAnnotation(ClassAnno.class);
			if (classAlias == null || StringUtils.isEmpty(classAlias.validateParentClass())) {
				break;
			}
			try {
				parentClass = Class.forName(classAlias.validateParentClass());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if (parentClass == null)
				break;

			try {
				parentField = parentClass.getDeclaredField(field.getName());
			} catch (Exception e) {
			}
			if (!parentField.getType().equals(field.getType())) {
				parentField = null;
				break;
			}
			currentClass = parentClass;
		}

		if (parentField == null)
			return null;

		return (T) parentField.getAnnotation((Class) annotationClass);
	}

	/**
	 * 
	 * @param clasz
	 * @param field
	 * @return
	 */
	public static Annotation[] getFieldValidateAnnotation(Field field) {
		ParentValidate validate = field.getAnnotation(ParentValidate.class);
		if (validate == null) {
			return field.getAnnotations();
		}

		/**
		 * ���Ҹ���͸���
		 */
		Class<?> currentClass = field.getDeclaringClass();
		Field parentField = null;
		while (parentField == null || parentField.getAnnotation(ParentValidate.class) != null) {
			parentField = null;
			Class<?> parentClass = null;
			ClassAnno classAlias = currentClass.getAnnotation(ClassAnno.class);
			if (classAlias == null || StringUtils.isEmpty(classAlias.validateParentClass())) {
				break;
			}
			try {
				parentClass = Class.forName(classAlias.validateParentClass());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if (parentClass == null)
				break;

			try {
				parentField = parentClass.getDeclaredField(field.getName());
			} catch (Exception e) {
			}
			currentClass = parentClass;
		}

		if (parentField == null)
			return null;

		return parentField.getAnnotations();

	}

	public static String getFieldAlias(Field field, String defaultValue) {
		String alias = getFieldAlias(field);
		if (StringUtils.isEmpty(alias)) {
			return defaultValue;
		}
		return alias;
	}

	public static Object getFieldValue(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			logger.error("��ȡ����ֵ�쳣,field=" + field + ",obj=" + obj, e);
			return null;
		}
	}

	public static Object getFieldValue(Object object, String fieldName) {
		if (object == null) {
			return null;
		}
		Field field = getFieldSafe(object.getClass(), fieldName);
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			logger.error("��ȡ����ֵ�쳣,fieldName=" + fieldName + ",object=" + object, e);
			return null;
		}
	}

	/**
	 * �õ�field�ı���
	 * 
	 * @param clasz
	 * @param field
	 * @return
	 */
	public static String getFieldAlias(Field field) {
		FieldAnno fieldAlias = field.getAnnotation(FieldAnno.class);
		if (fieldAlias == null)
			return null;

		if (!StringUtils.isEmpty(fieldAlias.alias())) {
			return fieldAlias.alias();
		}

		/**
		 * ���Ҹ���͸���
		 */

		Class<?> currentClass = field.getDeclaringClass();
		Field parentField = null;
		while (parentField == null || parentField.getAnnotation(FieldAnno.class) == null || StringUtils.isEmpty(parentField.getAnnotation(FieldAnno.class).alias())) {
			parentField = null;

			ClassAnno classAlias = currentClass.getAnnotation(ClassAnno.class);
			if (classAlias == null || StringUtils.isEmpty(classAlias.aliasParentClass())) {
				return null;
			}
			Class parentClass = null;
			try {
				parentClass = Class.forName(classAlias.aliasParentClass());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if (parentClass == null)
				return null;
			try {
				parentField = parentClass.getDeclaredField(field.getName());
			} catch (Exception e) {
			}
			currentClass = parentClass;
		}

		if (parentField == null) {
			return null;
		}
		return getFieldAlias(parentField);
	}

	public static String getFieldNameByGetterMethod(String getterMethodName) {
		if (StringUtils.startsWith(getterMethodName, "get")) {
			return StringUtils.toCamelCase(getterMethodName.substring(3));
		}
		if (StringUtils.startsWith(getterMethodName, "is")) {
			return StringUtils.toCamelCase(getterMethodName.substring(2));
		}
		return null;
	}
}

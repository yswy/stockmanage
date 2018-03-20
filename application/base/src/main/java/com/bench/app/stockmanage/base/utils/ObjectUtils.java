package com.bench.app.stockmanage.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author chenbug
 * @version $Id: ObjectUtils.java,v 0.1 2009-5-21 ����12:13:44 chenbug Exp $
 */
public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {

	public static final Logger log = Logger.getLogger(ObjectUtils.class);

	public static final ObjectUtils INSTANCE = new ObjectUtils();
	/*
	 * ==========================================================================
	 * ==
	 */
	/* ������singleton�� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ���ڱ�ʾ<code>null</code>�ĳ�����
	 * 
	 * <p>
	 * ���磬<code>HashMap.get(Object)</code>��������<code>null</code>�����ֿ��ܣ� ֵ�����ڻ�ֵΪ
	 * <code>null</code>�������singleton�������������������Ρ�
	 * </p>
	 * 
	 * <p>
	 * ��һ�������ǣ�<code>Hashtable</code>��ֵ����Ϊ<code>null</code>��
	 * </p>
	 */
	public static final Object NULL = new Serializable() {
		private static final long serialVersionUID = 7092611880189329093L;

		private Object readResolve() {
			return NULL;
		}
	};

	/**
	 * �Ƿ�Ϊnull
	 * 
	 * @param object
	 * @return
	 */
	public static final boolean isNull(Object object) {
		return object == null;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* Ĭ��ֵ������ */
	/*                                                                              */
	/* ������Ϊnullʱ��������ת����ָ����Ĭ�϶��� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * �������Ϊ<code>null</code>���򷵻�ָ��Ĭ�϶��󣬷��򷵻ض�����
	 * 
	 * <pre>
	 * ObjectUtil.defaultIfNull(null, null)      = null
	 * ObjectUtil.defaultIfNull(null, &quot;&quot;)        = &quot;&quot;
	 * ObjectUtil.defaultIfNull(null, &quot;zz&quot;)      = &quot;zz&quot;
	 * ObjectUtil.defaultIfNull(&quot;abc&quot;, *)        = &quot;abc&quot;
	 * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
	 * </pre>
	 * 
	 * @param object
	 *            Ҫ���ԵĶ���
	 * @param defaultValue
	 *            Ĭ��ֵ
	 * 
	 * @return �������Ĭ�϶���
	 */
	public static Object defaultIfNull(Object object, Object defaultValue) {
		return (object != null) ? object : defaultValue;
	}

	/**
	 * �������Ϊ<code>null</code>���򷵻�ָ��Ĭ�϶��󣬷��򷵻ض�����
	 * 
	 * @param object
	 * @param defaultValue
	 * @return
	 */
	public static <T> T defaultWhenNull(T object, T defaultValue) {
		return (object != null) ? object : defaultValue;
	}

	/**
	 * @param list
	 * @return
	 */
	public static boolean equalsList(List<?> list) {
		if (list == null)
			return false;
		if (list.size() == 0)
			return true;

		Object firstObject = list.get(0);
		for (Object object : list) {
			if (!ObjectUtils.equals(firstObject, object)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* Hashcode������ */
	/*                                                                              */
	/* ���·�������ȡ�ö����hash code�� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ȡ�ö����hashֵ, �������Ϊ<code>null</code>, �򷵻�<code>0</code>��
	 * 
	 * <p>
	 * �˷���������ȷ�ش����ά���顣
	 * </p>
	 * 
	 * @param object
	 *            ����
	 * 
	 * @return hashֵ
	 */
	public static int hashCode(Object object) {
		return ArrayUtils.hashCode(object);
	}

	/**
	 * ȡ�ö����ԭʼ��hashֵ, �������Ϊ<code>null</code>, �򷵻�<code>0</code>��
	 * 
	 * <p>
	 * �÷���ʹ��<code>System.identityHashCode</code>��ȡ��hashֵ����ֵ���ܶ������
	 * <code>hashCode</code>������Ӱ�졣
	 * </p>
	 * 
	 * @param object
	 *            ����
	 * 
	 * @return hashֵ
	 */
	public static int identityHashCode(Object object) {
		return (object == null) ? 0 : System.identityHashCode(object);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* ȡ�ö����identity�� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ȡ�ö��������identity����ͬ����û�и���<code>toString()</code>����ʱ��
	 * <code>Object.toString()</code>��ԭʼ�����
	 * 
	 * <pre>
	 * ObjectUtil.identityToString(null)          = null
	 * ObjectUtil.identityToString(&quot;&quot;)            = &quot;java.lang.String@1e23&quot;
	 * ObjectUtil.identityToString(Boolean.TRUE)  = &quot;java.lang.Boolean@7fa&quot;
	 * ObjectUtil.identityToString(new int[0])    = &quot;int[]@7fa&quot;
	 * ObjectUtil.identityToString(new Object[0]) = &quot;java.lang.Object[]@7fa&quot;
	 * </pre>
	 * 
	 * @param object
	 *            ����
	 * 
	 * @return �����identity�����������<code>null</code>���򷵻�<code>null</code>
	 */
	public static String identityToString(Object object) {
		if (object == null) {
			return null;
		}

		return appendIdentityToString(null, object).toString();
	}

	/**
	 * ȡ�ö��������identity����ͬ����û�и���<code>toString()</code>����ʱ��
	 * <code>Object.toString()</code>��ԭʼ�����
	 * 
	 * <pre>
	 * ObjectUtil.identityToString(null, &quot;NULL&quot;)            = &quot;NULL&quot;
	 * ObjectUtil.identityToString(&quot;&quot;, &quot;NULL&quot;)              = &quot;java.lang.String@1e23&quot;
	 * ObjectUtil.identityToString(Boolean.TRUE, &quot;NULL&quot;)    = &quot;java.lang.Boolean@7fa&quot;
	 * ObjectUtil.identityToString(new int[0], &quot;NULL&quot;)      = &quot;int[]@7fa&quot;
	 * ObjectUtil.identityToString(new Object[0], &quot;NULL&quot;)   = &quot;java.lang.Object[]@7fa&quot;
	 * </pre>
	 * 
	 * @param object
	 *            ����
	 * @param nullStr
	 *            �������Ϊ<code>null</code>���򷵻ظ��ַ���
	 * 
	 * @return �����identity�����������<code>null</code>���򷵻�ָ���ַ���
	 */
	public static String identityToString(Object object, String nullStr) {
		if (object == null) {
			return nullStr;
		}

		return appendIdentityToString(null, object).toString();
	}

	/**
	 * �����������identity������ͬ����û�и���<code>toString()</code>����ʱ��
	 * <code>Object.toString()</code>��ԭʼ�������׷�ӵ�<code>StringBuffer</code>�С�
	 * 
	 * <pre>
	 * ObjectUtil.appendIdentityToString(*, null)            = null
	 * ObjectUtil.appendIdentityToString(null, &quot;&quot;)           = &quot;java.lang.String@1e23&quot;
	 * ObjectUtil.appendIdentityToString(null, Boolean.TRUE) = &quot;java.lang.Boolean@7fa&quot;
	 * ObjectUtil.appendIdentityToString(buf, Boolean.TRUE)  = buf.append(&quot;java.lang.Boolean@7fa&quot;)
	 * ObjectUtil.appendIdentityToString(buf, new int[0])    = buf.append(&quot;int[]@7fa&quot;)
	 * ObjectUtil.appendIdentityToString(buf, new Object[0]) = buf.append(&quot;java.lang.Object[]@7fa&quot;)
	 * </pre>
	 * 
	 * @param buffer
	 *            <code>StringBuffer</code>���������<code>null</code>���򴴽��µ�
	 * @param object
	 *            ����
	 * 
	 * @return <code>StringBuffer</code>�����������Ϊ<code>null</code>���򷵻�
	 *         <code>null</code>
	 */
	public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
		if (object == null) {
			return null;
		}

		if (buffer == null) {
			buffer = new StringBuffer();
		}

		buffer.append(ClassUtils.getClassNameForObject(object));

		return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* Clone������ */
	/*                                                                              */
	/* ���·�������Object.clone������Ĭ���ǡ�ǳ���ơ���shallow copy���� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ����һ�������������Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <p>
	 * �˷�������<code>Object.clone</code>������Ĭ��ֻ���С�ǳ���ơ��� �������飬����
	 * <code>ArrayUtils.clone</code>��������Ч��
	 * </p>
	 * 
	 * @param array
	 *            Ҫ���Ƶ�����
	 * 
	 * @return ����ĸ��������ԭʼ����Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static Object clone(Object array) {
		if (array == null) {
			return null;
		}

		// ���������⴦��
		if (array instanceof Object[]) {
			return ArrayUtils.clone((Object[]) array);
		}

		if (array instanceof long[]) {
			return ArrayUtils.clone((long[]) array);
		}

		if (array instanceof int[]) {
			return ArrayUtils.clone((int[]) array);
		}

		if (array instanceof short[]) {
			return ArrayUtils.clone((short[]) array);
		}

		if (array instanceof byte[]) {
			return ArrayUtils.clone((byte[]) array);
		}

		if (array instanceof double[]) {
			return ArrayUtils.clone((double[]) array);
		}

		if (array instanceof float[]) {
			return ArrayUtils.clone((float[]) array);
		}

		if (array instanceof boolean[]) {
			return ArrayUtils.clone((boolean[]) array);
		}

		if (array instanceof char[]) {
			return ArrayUtils.clone((char[]) array);
		}

		// Not cloneable
		if (!(array instanceof Cloneable)) {
			throw new RuntimeException("Object of class " + array.getClass().getName() + " is not Cloneable");
		}

		// ��reflection����clone����
		Class clazz = array.getClass();

		try {
			Method cloneMethod = clazz.getMethod("clone", ArrayUtils.EMPTY_CLASS_ARRAY);

			return cloneMethod.invoke(array, ArrayUtils.EMPTY_OBJECT_ARRAY);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* �Ƚ϶�������͡� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ������������Ƿ�������ͬ���͡�<code>null</code>���������������͡�
	 * 
	 * @param object1
	 *            ����1
	 * @param object2
	 *            ����2
	 * 
	 * @return ���������������ͬ�����ͣ��򷵻�<code>true</code>
	 */
	public static boolean isSameType(Object object1, Object object2) {
		if ((object1 == null) || (object2 == null)) {
			return true;
		}

		return object1.getClass().equals(object2.getClass());
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* toString������ */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ȡ�ö����<code>toString()</code>��ֵ���������Ϊ<code>null</code>���򷵻ؿ��ַ���
	 * <code>""</code>��
	 * 
	 * <pre>
	 * ObjectUtil.toString(null)         = &quot;&quot;
	 * ObjectUtil.toString(&quot;&quot;)           = &quot;&quot;
	 * ObjectUtil.toString(&quot;bat&quot;)        = &quot;bat&quot;
	 * ObjectUtil.toString(Boolean.TRUE) = &quot;true&quot;
	 * ObjectUtil.toString([1, 2, 3])    = &quot;[1, 2, 3]&quot;
	 * </pre>
	 * 
	 * @param object
	 *            ����
	 * 
	 * @return �����<code>toString()</code>�ķ���ֵ������ַ���<code>""</code>
	 */
	public static String toString(Object object) {
		return (object == null) ? StringUtils.EMPTY_STRING : (object.getClass().isArray() ? ArrayUtils.toString(object) : object.toString());
	}

	/**
	 * ȡ�ö����<code>toString()</code>��ֵ���������Ϊ<code>null</code>���򷵻�ָ���ַ�����
	 * 
	 * <pre>
	 * ObjectUtil.toString(null, null)           = null
	 * ObjectUtil.toString(null, &quot;null&quot;)         = &quot;null&quot;
	 * ObjectUtil.toString(&quot;&quot;, &quot;null&quot;)           = &quot;&quot;
	 * ObjectUtil.toString(&quot;bat&quot;, &quot;null&quot;)        = &quot;bat&quot;
	 * ObjectUtil.toString(Boolean.TRUE, &quot;null&quot;) = &quot;true&quot;
	 * ObjectUtil.toString([1, 2, 3], &quot;null&quot;)    = &quot;[1, 2, 3]&quot;
	 * </pre>
	 * 
	 * @param object
	 *            ����
	 * @param nullStr
	 *            �������Ϊ<code>null</code>���򷵻ظ��ַ���
	 * 
	 * @return �����<code>toString()</code>�ķ���ֵ����ָ���ַ���
	 */
	public static String toString(Object object, String nullStr) {
		return (object == null) ? nullStr : (object.getClass().isArray() ? ArrayUtils.toString(object) : object.toString());
	}

	/**
	 * ��������¡
	 * 
	 * @param obj
	 *            ����¡�Ķ���
	 * @return �����Ķ��󸱱�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T deepClone(T obj) {
		// ������д������
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		ByteArrayInputStream bi = null;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj); // �����������
			bi = new ByteArrayInputStream(bo.toByteArray());
			oi = new ObjectInputStream(bi);
			return (T) oi.readObject();
		} catch (Exception e) {
			log.error("��������쳣������null,object=" + obj, e);
			return null;
		} finally {
			IOUtils.closeQuietly(bo);
			IOUtils.closeQuietly(oo);
			IOUtils.closeQuietly(oi);
			IOUtils.closeQuietly(bi);
		}
	}

	/**
	 * �Ƿ�Ϊnull
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isString(Object object) {
		return object != null && object instanceof String;
	}

	/**
	 * �Ƿ�Ϊnull
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isObject(Object object) {
		return object == null;
	}

	/**
	 * ��valueList�е�ÿ������,��propertyName��Ӧ��ֵ����
	 * 
	 * @param valueList
	 * @param propertyName
	 * @return
	 */
	public static <V> Map<Object, List<V>> sortByPropertyValue(List<V> valueList, String propertyName) {
		Map<Object, List<V>> returnMap = new HashMap<Object, List<V>>();
		if (valueList == null) {
			return returnMap;
		}
		for (V v : valueList) {
			Object propertyValue = null;
			try {
				propertyValue = PropertyUtils.getProperty(v, propertyName);
			} catch (Exception e) {
				throw new RuntimeException("��ȡ����ֵ�쳣,value=" + v + ",propertyName=" + propertyName, e);
			}
			List<V> subList = returnMap.get(propertyValue);
			if (subList == null) {
				subList = new ArrayList<V>();
				returnMap.put(propertyValue, subList);
			}
			subList.add(v);

		}
		return returnMap;
	}
}

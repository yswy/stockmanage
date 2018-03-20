package com.bench.app.stockmanage.base.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.redisson.api.RObject;
import org.springframework.util.Assert;

/**
 * �й� <code>Class</code> ����Ĺ����ࡣ
 * 
 * <p>
 * ������е�ÿ�����������ԡ���ȫ���ش��� <code>null</code> ���������׳�
 * <code>NullPointerException</code>��
 * </p>
 * 
 * @author Michael Zhou
 * @version $Id: ClassUtil.java 509 2004-02-16 05:42:07Z baobao $
 */
public class ClassUtils {

	private static Logger logger = Logger.getLogger(ClassUtils.class);

	public static final ClassUtils INSTANCE = new ClassUtils();

	/**
	 * ����һ��ʵ��
	 * 
	 * @param clasz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clasz) {
		try {
			return clasz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("ʵ�������쳣,class=" + clasz);
		}
	}

	public static <T> T newInstance(Class<T> clazz, Class<?>[] parameterClasses, Object[] parameterValues) {
		Assert.notNull(clazz, "Class must not be null");
		try {
			Constructor<T> c = clazz.getConstructor(parameterClasses);
			return c == null ? null : c.newInstance(parameterValues);
		} catch (Exception ex) {
			logger.error("����ʵ���쳣,class=" + clazz, ex);
			return null;
		}
	}

	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		try {
			return clazz.getConstructor(paramTypes);
		} catch (NoSuchMethodException ex) {
			return null;
		}
	}

	/**
	 * ����һ��ʵ��
	 * 
	 * @param clasz
	 * @return
	 */
	public static Object newInstance(String clasz) {
		try {
			return Class.forName(clasz).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("ʵ�������쳣,class=" + clasz);
		}
	}
	/*
	 * =========================================================================
	 * = ==
	 */
	/* ������singleton�� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/** ��Դ�ļ��ķָ����� <code>'/'</code>�� */
	public static final char RESOURCE_SEPARATOR_CHAR = '/';

	/** Java�����ķָ����� <code>'.'</code>�� */
	public static final char PACKAGE_SEPARATOR_CHAR = '.';

	/** Java�����ķָ����� <code>"."</code>�� */
	public static final String PACKAGE_SEPARATOR = String.valueOf(PACKAGE_SEPARATOR_CHAR);

	/** ������ķָ����� <code>'$'</code>�� */
	public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

	/** ������ķָ����� <code>"$"</code>�� */
	public static final String INNER_CLASS_SEPARATOR = String.valueOf(INNER_CLASS_SEPARATOR_CHAR);

	/** ���������Ϣ����������, �ӿ�, �����ά������Ϣ�� */
	private static Map TYPE_MAP = Collections.synchronizedMap(new WeakHashMap());

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ȡ��������package���ķ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ȡ�ö������������ֱ��������
	 * 
	 * <p>
	 * �൱�� <code>object.getClass().getName()</code> ������ͬ���ǣ��÷����ø�ֱ�۵ķ�ʽ��ʾ�������͡� ���磺
	 * 
	 * <pre>
	 *  int[].class.getName() = &quot;[I&quot; ClassUtil.getClassName(int[].class) = &quot;int[]&quot;
	 * 
	 *  Integer[][].class.getName() = &quot;[[Ljava.lang.Integer;&quot; ClassUtil.getClassName(Integer[][].class) = &quot;java.lang.Integer[][]&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * ���ڷ���������ͣ��÷�����Ч�� <code>Class.getName()</code> ������
	 * </p>
	 * 
	 * <p>
	 * ע�⣬�÷��������ص���������ֻ��������ʾ���˿����������� <code>Class.forName</code> ������
	 * </p>
	 * 
	 * @param object
	 *            Ҫ��ʾ�����Ķ���
	 * 
	 * @return ������ʾ��ֱ�����������ԭ����Ϊ�ջ�Ƿ����򷵻� <code>null</code>
	 */
	public static String getClassNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getClassName(object.getClass().getName(), true);
	}

	public static Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(methodName, "Method name must not be null");
		try {
			return clazz.getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException ex) {
			return null;
		}
	}

	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
		for (Class<?> c : getClassHierarchy(clazz)) {
			if (c.getAnnotation(annotationClass) != null) {
				return c.getAnnotation(annotationClass);
			}
		}
		return null;
	}

	private static Iterable<Class<?>> getClassHierarchy(Class<?> clazz) {
		// Don't descend into hierarchy for RObjects
		if (Arrays.asList(clazz.getInterfaces()).contains(RObject.class)) {
			return Collections.<Class<?>> singleton(clazz);
		}
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
			classes.add(c);
		}
		return classes;
	}

	/**
	 * ȡ��ֱ�۵�������
	 * 
	 * <p>
	 * �൱�� <code>clazz.getName()</code> ������ͬ���ǣ��÷����ø�ֱ�۵ķ�ʽ��ʾ�������͡� ���磺
	 * 
	 * <pre>
	 *  int[].class.getName() = &quot;[I&quot; ClassUtil.getClassName(int[].class) = &quot;int[]&quot;
	 * 
	 *  Integer[][].class.getName() = &quot;[[Ljava.lang.Integer;&quot; ClassUtil.getClassName(Integer[][].class) = &quot;java.lang.Integer[][]&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * ���ڷ���������ͣ��÷�����Ч�� <code>Class.getName()</code> ������
	 * </p>
	 * 
	 * <p>
	 * ע�⣬�÷��������ص���������ֻ��������ʾ���˿����������� <code>Class.forName</code> ������
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ��ʾ��������
	 * 
	 * @return ������ʾ��ֱ�����������ԭʼ��Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getClassName(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getClassName(clazz.getName(), true);
	}

	/**
	 * ȡ��ֱ�۵�������
	 * 
	 * <p>
	 * <code>className</code> �����Ǵ� <code>clazz.getName()</code>
	 * �����صĺϷ��������÷����ø�ֱ�۵ķ�ʽ��ʾ�������͡� ���磺
	 * 
	 * <pre>
	 *  int[].class.getName() = &quot;[I&quot; ClassUtil.getClassName(int[].class) = &quot;int[]&quot;
	 * 
	 *  Integer[][].class.getName() = &quot;[[Ljava.lang.Integer;&quot; ClassUtil.getClassName(Integer[][].class) = &quot;java.lang.Integer[][]&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * ���ڷ���������ͣ��÷�����Ч�� <code>Class.getName()</code> ������
	 * </p>
	 * 
	 * <p>
	 * ע�⣬�÷��������ص���������ֻ��������ʾ���˿����������� <code>Class.forName</code> ������
	 * </p>
	 * 
	 * @param className
	 *            Ҫ��ʾ������
	 * 
	 * @return ������ʾ��ֱ�����������ԭ����Ϊ <code>null</code> ���򷵻� <code>null</code>
	 *         �����ԭ�����ǷǷ��ģ��򷵻�ԭ����
	 */
	public static String getClassName(String className) {
		return getClassName(className, true);
	}

	/**
	 * ȡ��ֱ�۵�������
	 * 
	 * @param className
	 *            ����
	 * @param processInnerClass
	 *            �Ƿ�������ָ��� <code>'$'</code> ת���� <code>'.'</code>
	 * 
	 * @return ֱ�۵��������� <code>null</code>
	 */
	private static String getClassName(String className, boolean processInnerClass) {
		if (StringUtils.isEmpty(className)) {
			return className;
		}

		if (processInnerClass) {
			className = className.replace(INNER_CLASS_SEPARATOR_CHAR, PACKAGE_SEPARATOR_CHAR);
		}

		int length = className.length();
		int dimension = 0;

		// ȡ�������ά��������������飬ά��Ϊ0
		for (int i = 0; i < length; i++, dimension++) {
			if (className.charAt(i) != '[') {
				break;
			}
		}

		// ����������飬��ֱ�ӷ���
		if (dimension == 0) {
			return className;
		}

		// ȷ�������Ϸ�
		if (length <= dimension) {
			return className; // �Ƿ�����
		}

		// ��������
		StringBuffer componentTypeName = new StringBuffer();

		switch (className.charAt(dimension)) {
		case 'Z':
			componentTypeName.append("boolean");
			break;

		case 'B':
			componentTypeName.append("byte");
			break;

		case 'C':
			componentTypeName.append("char");
			break;

		case 'D':
			componentTypeName.append("double");
			break;

		case 'F':
			componentTypeName.append("float");
			break;

		case 'I':
			componentTypeName.append("int");
			break;

		case 'J':
			componentTypeName.append("long");
			break;

		case 'S':
			componentTypeName.append("short");
			break;

		case 'L':

			if ((className.charAt(length - 1) != ';') || (length <= (dimension + 2))) {
				return className; // �Ƿ�����
			}

			componentTypeName.append(className.substring(dimension + 1, length - 1));
			break;

		default:
			return className; // �Ƿ�����
		}

		for (int i = 0; i < dimension; i++) {
			componentTypeName.append("[]");
		}

		return componentTypeName.toString();
	}

	/**
	 * ȡ��ָ��������������Ķ�������������package����
	 * 
	 * <p>
	 * �˷���������ȷ��ʾ���������������ơ�
	 * </p>
	 * 
	 * <p>
	 * ���磺
	 * 
	 * <pre>
	 *  ClassUtil.getShortClassNameForObject(Boolean.TRUE) = &quot;Boolean&quot; ClassUtil.getShortClassNameForObject(new Boolean[10]) = &quot;Boolean[]&quot; ClassUtil.getShortClassNameForObject(new int[1][2]) = &quot;int[][]&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param object
	 *            Ҫ�鿴�Ķ���
	 * 
	 * @return ���������������Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getShortClassNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getShortClassName(object.getClass().getName());
	}

	/**
	 * ȡ�ö�������������package����
	 * 
	 * <p>
	 * �˷���������ȷ��ʾ���������������ơ�
	 * </p>
	 * 
	 * <p>
	 * ���磺
	 * 
	 * <pre>
	 *  ClassUtil.getShortClassName(Boolean.class) = &quot;Boolean&quot; ClassUtil.getShortClassName(Boolean[].class) = &quot;Boolean[]&quot; ClassUtil.getShortClassName(int[][].class) = &quot;int[][]&quot; ClassUtil.getShortClassName(Map.Entry.class) = &quot;Map.Entry&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ�鿴����
	 * 
	 * @return �������������Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getShortClassName(Class clazz) {
		if (clazz == null) {
			return null;
		}

		return getShortClassName(clazz.getName());
	}

	/**
	 * ȡ��������������package����
	 * 
	 * <p>
	 * �˷���������ȷ��ʾ���������������ơ�
	 * </p>
	 * 
	 * <p>
	 * ���磺
	 * 
	 * <pre>
	 *  ClassUtil.getShortClassName(Boolean.class.getName()) = &quot;Boolean&quot; ClassUtil.getShortClassName(Boolean[].class.getName()) = &quot;Boolean[]&quot; ClassUtil.getShortClassName(int[][].class.getName()) = &quot;int[][]&quot; ClassUtil.getShortClassName(Map.Entry.class.getName()) = &quot;Map.Entry&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param className
	 *            Ҫ�鿴������
	 * 
	 * @return ���������������Ϊ�գ��򷵻� <code>null</code>
	 */
	public static String getShortClassName(String className) {
		if (StringUtils.isEmpty(className)) {
			return className;
		}

		// ת����ֱ�۵�����
		className = getClassName(className, false);

		char[] chars = className.toCharArray();
		int lastDot = 0;

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == PACKAGE_SEPARATOR_CHAR) {
				lastDot = i + 1;
			} else if (chars[i] == INNER_CLASS_SEPARATOR_CHAR) {
				chars[i] = PACKAGE_SEPARATOR_CHAR;
			}
		}

		return new String(chars, lastDot, chars.length - lastDot);
	}

	/**
	 * ȡ��ָ���������������package����
	 * 
	 * <p>
	 * �������飬�˷������ص�������Ԫ�����͵�package����
	 * </p>
	 * 
	 * @param object
	 *            Ҫ�鿴�Ķ���
	 * 
	 * @return package�����������Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getPackageNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getPackageName(object.getClass().getName());
	}

	/**
	 * ȡ��ָ�����package����
	 * 
	 * <p>
	 * �������飬�˷������ص�������Ԫ�����͵�package����
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ�鿴����
	 * 
	 * @return package���������Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getPackageName(Class clazz) {
		if (clazz == null) {
			return null;
		}

		return getPackageName(clazz.getName());
	}

	/**
	 * ȡ��ָ��������package����
	 * 
	 * <p>
	 * �������飬�˷������ص�������Ԫ�����͵�package����
	 * </p>
	 * 
	 * @param className
	 *            Ҫ�鿴������
	 * 
	 * @return package�����������Ϊ�գ��򷵻� <code>null</code>
	 */
	public static String getPackageName(String className) {
		if (StringUtils.isEmpty(className)) {
			return null;
		}

		// ת����ֱ�۵�����
		className = getClassName(className, false);

		int i = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);

		if (i == -1) {
			return "";
		}

		return className.substring(0, i);
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ȡ��������package����resource���ķ����� */
	/*                                                                              */
	/* ��������package����ͬ���ǣ�resource�������ļ��������淶�����磺 */
	/* java/lang/String.class */
	/* com/bench/commons/lang */
	/* etc. */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ȡ�ö��������������Դ����
	 * 
	 * <p>
	 * ���磺
	 * 
	 * <pre>
	 * ClassUtil.getClassNameForObjectAsResource(&quot;This is a string&quot;) = &quot;java/lang/String.class&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param object
	 *            Ҫ��ʾ�����Ķ���
	 * 
	 * @return ָ���������������Դ�����������Ϊ�գ��򷵻�<code>null</code>
	 */
	public static String getClassNameForObjectAsResource(Object object) {
		if (object == null) {
			return null;
		}

		return object.getClass().getName().replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR) + ".class";
	}

	/**
	 * ȡ��ָ�������Դ����
	 * 
	 * <p>
	 * ���磺
	 * 
	 * <pre>
	 * ClassUtil.getClassNameAsResource(String.class) = &quot;java/lang/String.class&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ��ʾ��������
	 * 
	 * @return ָ�������Դ�������ָ����Ϊ�գ��򷵻�<code>null</code>
	 */
	public static String getClassNameAsResource(Class clazz) {
		if (clazz == null) {
			return null;
		}

		return clazz.getName().replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR) + ".class";
	}

	/**
	 * ȡ��ָ�������Դ����
	 * 
	 * <p>
	 * ���磺
	 * 
	 * <pre>
	 * ClassUtil.getClassNameAsResource(&quot;java.lang.String&quot;) = &quot;java/lang/String.class&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param className
	 *            Ҫ��ʾ������
	 * 
	 * @return ָ��������Ӧ����Դ�������ָ������Ϊ�գ��򷵻�<code>null</code>
	 */
	public static String getClassNameAsResource(String className) {
		if (className == null) {
			return null;
		}

		return className.replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR) + ".class";
	}

	/**
	 * ȡ��ָ���������������package������Դ����
	 * 
	 * <p>
	 * �������飬�˷������ص�������Ԫ�����͵�package����
	 * </p>
	 * 
	 * @param object
	 *            Ҫ�鿴�Ķ���
	 * 
	 * @return package�����������Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getPackageNameForObjectAsResource(Object object) {
		if (object == null) {
			return null;
		}

		return getPackageNameForObject(object).replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR);
	}

	/**
	 * ȡ��ָ�����package������Դ����
	 * 
	 * <p>
	 * �������飬�˷������ص�������Ԫ�����͵�package����
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ�鿴����
	 * 
	 * @return package���������Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static String getPackageNameAsResource(Class clazz) {
		if (clazz == null) {
			return null;
		}

		return getPackageName(clazz).replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR);
	}

	/**
	 * ȡ��ָ��������package������Դ����
	 * 
	 * <p>
	 * �������飬�˷������ص�������Ԫ�����͵�package����
	 * </p>
	 * 
	 * @param className
	 *            Ҫ�鿴������
	 * 
	 * @return package�����������Ϊ�գ��򷵻� <code>null</code>
	 */
	public static String getPackageNameAsResource(String className) {
		if (className == null) {
			return null;
		}

		return getPackageName(className).replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR);
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ȡ�������Ϣ���縸��, �ӿ�, �����ά���ȡ� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ȡ��ָ��ά���� <code>Array</code>��.
	 * 
	 * @param componentType
	 *            ����Ļ���
	 * @param dimension
	 *            ά�������С�� <code>0</code> ���� <code>0</code>
	 * 
	 * @return ���ά��Ϊ0, �򷵻ػ��౾��, ���򷵻������࣬�������Ļ���Ϊ <code>null</code> ���򷵻�
	 *         <code>null</code>
	 */
	public static Class getArrayClass(Class componentType, int dimension) {
		if (dimension <= 0) {
			return componentType;
		}

		if (componentType == null) {
			return null;
		}

		return Array.newInstance(componentType, new int[dimension]).getClass();
	}

	/**
	 * ȡ������Ԫ�ص����͡�
	 * 
	 * @param type
	 *            Ҫ���ҵ���
	 * 
	 * @return ���������, �򷵻�����Ԫ�ص�����, ���򷵻� <code>null</code>
	 */
	public static Class getArrayComponentType(Class type) {
		if (type == null) {
			return null;
		}

		return getTypeInfo(type).getArrayComponentType();
	}

	/**
	 * ȡ�������ά����
	 * 
	 * @param clazz
	 *            Ҫ���ҵ���
	 * 
	 * @return �����ά��. �����������, �򷵻� <code>0</code> ���������Ϊ <code>null</code> ���Ƿ���
	 *         <code>-1</code>
	 */
	public static int getArrayDimension(Class clazz) {
		if (clazz == null) {
			return -1;
		}

		return getTypeInfo(clazz).getArrayDimension();
	}

	/**
	 * ȡ��ָ��������и��ࡣ
	 * 
	 * <p>
	 * ����һ�� <code>Class</code> ʵ������������ǽӿڣ�Ҳ�������飬�˷��������г��Ӹ���ĸ��࿪ʼֱ��
	 * <code>Object</code> �������ࡣ
	 * </p>
	 * 
	 * <p>
	 * ���� <code>ClassUtil.getSuperclasses(java.util.ArrayList.class)</code>
	 * ���������б�
	 * 
	 * <ol>
	 * <li><code>java.util.AbstractList</code></li>
	 * <li><code>java.util.AbstractCollection</code></li>
	 * <li><code>java.lang.Object</code></li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * ����һ���ӿڣ��˷�������һ�����б�
	 * </p>
	 * 
	 * <p>
	 * ����<code>ClassUtil.getSuperclasses(java.util.List.class)</code>������һ�����б�
	 * </p>
	 * 
	 * <p>
	 * ����һ�����飬�˷�������һ���б��г�����component���͵ĸ������ͬά�����������͡� ���磺
	 * <code>ClassUtil.getSuperclasses(java.util.ArrayList[][].class)</code>
	 * ���������б�
	 * 
	 * <ol>
	 * <li><code>java.util.AbstractList[][]</code></li>
	 * <li><code>java.util.AbstractCollection[][]</code></li>
	 * <li><code>java.lang.Object[][]</code></li>
	 * <li><code>java.lang.Object[]</code></li>
	 * <li><code>java.lang.Object</code></li>
	 * </ol>
	 * 
	 * ע�⣬ԭ�����ͼ������飬����ת������Ӧ�İ�װ�������� ���磺
	 * <code>ClassUtil.getSuperclasses(int[][].class)</code> ���������б�
	 * 
	 * <ol>
	 * <li><code>java.lang.Number[][]</code></li>
	 * <li><code>java.lang.Object[][]</code></li>
	 * <li><code>java.lang.Object[]</code></li>
	 * <li><code>java.lang.Object</code></li>
	 * </ol>
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ���ҵ���
	 * 
	 * @return ���и�����б����ָ����Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static List<Class<?>> getSuperclasses(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getTypeInfo(clazz).getSuperclasses();
	}

	/**
	 * ȡ��ָ��������нӿڡ�
	 * 
	 * <p>
	 * ����һ�� <code>Class</code> ʵ������������ǽӿڣ�Ҳ�������飬�˷��������г��Ӹ���ĸ��࿪ʼֱ��
	 * <code>Object</code> �������ࡣ
	 * </p>
	 * 
	 * <p>
	 * ���� <code>ClassUtil.getInterfaces(java.util.ArrayList.class)</code>
	 * ���������б�
	 * 
	 * <ol>
	 * <li><code>java.util.List</code></li>
	 * <li><code>java.util.Collection</code></li>
	 * <li><code>java.util.RandomAccess</code></li>
	 * <li><code>java.lang.Cloneable</code></li>
	 * <li><code>java.io.Serializable</code></li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * ����һ�����飬�˷�������һ���б��г�����component���͵Ľӿڵ���ͬά�����������͡� ���磺
	 * <code>ClassUtil.getInterfaces(java.util.ArrayList[][].class)</code>
	 * ���������б�
	 * 
	 * <ol>
	 * <li><code>java.util.List[][]</code></li>
	 * <li><code>java.util.Collection[][]</code></li>
	 * <li><code>java.util.RandomAccess[][]</code></li>
	 * <li><code>java.lang.Cloneable[][]</code></li>
	 * <li><code>java.io.Serializable[][]</code></li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * ע�⣬ԭ�����ͼ������飬����ת������Ӧ�İ�װ�������� ���磺
	 * <code>ClassUtil.getInterfaces(int[][].class)</code> ���������б�
	 * 
	 * <ol>
	 * <li><code>java.lang.Comparable[][]</code></li>
	 * <li><code>java.io.Serializable[][]</code></li>
	 * </ol>
	 * </p>
	 * 
	 * @param clazz
	 *            Ҫ���ҵ���
	 * 
	 * @return ���нӿڵ��б����ָ����Ϊ <code>null</code> ���򷵻� <code>null</code>
	 */
	public static List getInterfaces(Class clazz) {
		if (clazz == null) {
			return null;
		}

		return getTypeInfo(clazz).getInterfaces();
	}

	/**
	 * �ж�ָ�����Ƿ�Ϊ�����ࡣ
	 * 
	 * @param clazz
	 *            Ҫ���ҵ���
	 * 
	 * @return ����ǣ��򷵻� <code>true</code>
	 */
	public static boolean isInnerClass(Class clazz) {
		if (clazz == null) {
			return false;
		}

		return StringUtils.contains(clazz.getName(), INNER_CLASS_SEPARATOR_CHAR);
	}

	/**
	 * ���һ��ָ������ <code>fromClasses</code> �Ķ����Ƿ���Ը�ֵ����һ������ <code>classes</code>��
	 * 
	 * <p>
	 * �˷�����������ȷ��ָ�����͵Ĳ��� <code>object1, object2, ...</code> �Ƿ������������ȷ����������Ϊ
	 * <code>class1, class2,
	 * ...</code> �ķ�����
	 * </p>
	 * 
	 * <p>
	 * ���� <code>fromClasses</code> ��ÿ��Ԫ�� <code>fromClass</code> ��
	 * <code>classes</code> ��ÿ��Ԫ�� <code>clazz</code>�� �������¹���
	 * 
	 * <ol>
	 * <li>���Ŀ���� <code>clazz</code> Ϊ <code>null</code> �����Ƿ���
	 * <code>false</code>��</li>
	 * <li>����������� <code>fromClass</code> Ϊ <code>null</code> ������Ŀ������
	 * <code>clazz</code> Ϊ��ԭ�����ͣ��򷵻� <code>true</code>�� ��Ϊ <code>null</code>
	 * ���Ա������κ��������͡�</li>
	 * <li>���� <code>Class.isAssignableFrom</code> ������ȷ��Ŀ���� <code>clazz</code>
	 * �Ƿ�Ͳ����� <code>fromClass</code> ��ͬ�����丸�ࡢ�ӿڣ�����ǣ��򷵻� <code>true</code>��</li>
	 * <li>���Ŀ������ <code>clazz</code> Ϊԭ�����ͣ���ô����
	 * <a href="http://java.sun.com/docs/books/jls/">The Java Language
	 * Specification</a> ��sections 5.1.1, 5.1.2, 5.1.4�����Widening Primitive
	 * Conversion���򣬲������� <code>fromClass</code> �������κ�����չ�ɸ�Ŀ�����͵�ԭ�����ͼ����װ�ࡣ ���磬
	 * <code>clazz</code> Ϊ <code>long</code> ����ô�������Ϳ����� <code>byte</code>��
	 * <code>short</code>��<code>int</code>��<code>long</code>��<code>char</code>
	 * �����װ�� <code>java.lang.Byte</code>��<code>java.lang.Short</code>��
	 * <code>java.lang.Integer</code>�� <code>java.lang.Long</code> ��
	 * <code>java.lang.Character</code> �������������������򷵻� <code>true</code>��</li>
	 * <li>���������������������򷵻� <code>false</code>��</li>
	 * </ol>
	 * </p>
	 * 
	 * @param classes
	 *            Ŀ�������б������ <code>null</code> ���Ƿ��� <code>false</code>
	 * @param fromClasses
	 *            ���������б� <code>null</code> ��ʾ�ɸ�ֵ�������ԭ������
	 * 
	 * @return ������Ա���ֵ���򷵻� <code>true</code>
	 */
	public static boolean isAssignable(Class[] classes, Class[] fromClasses) {
		if (!ArrayUtils.isSameLength(fromClasses, classes)) {
			return false;
		}

		if (fromClasses == null) {
			fromClasses = ArrayUtils.EMPTY_CLASS_ARRAY;
		}

		if (classes == null) {
			classes = ArrayUtils.EMPTY_CLASS_ARRAY;
		}

		for (int i = 0; i < fromClasses.length; i++) {
			if (isAssignable(classes[i], fromClasses[i]) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * ���ָ������ <code>fromClass</code> �Ķ����Ƿ���Ը�ֵ����һ������ <code>clazz</code>��
	 * 
	 * <p>
	 * �˷�����������ȷ��ָ�����͵Ĳ��� <code>object1, object2, ...</code> �Ƿ������������ȷ����������
	 * <code>class1, class2,
	 * ...</code> �ķ�����
	 * </p>
	 * 
	 * <p>
	 * �������¹���
	 * 
	 * <ol>
	 * <li>���Ŀ���� <code>clazz</code> Ϊ <code>null</code> �����Ƿ���
	 * <code>false</code>��</li>
	 * <li>����������� <code>fromClass</code> Ϊ <code>null</code> ������Ŀ������
	 * <code>clazz</code> Ϊ��ԭ�����ͣ��򷵻� <code>true</code>�� ��Ϊ <code>null</code>
	 * ���Ա������κ��������͡�</li>
	 * <li>���� <code>Class.isAssignableFrom</code> ������ȷ��Ŀ���� <code>clazz</code>
	 * �Ƿ�Ͳ����� <code>fromClass</code> ��ͬ�����丸�ࡢ�ӿڣ�����ǣ��򷵻� <code>true</code>��</li>
	 * <li>���Ŀ������ <code>clazz</code> Ϊԭ�����ͣ���ô����
	 * <a href="http://java.sun.com/docs/books/jls/">The Java Language
	 * Specification</a> ��sections 5.1.1, 5.1.2, 5.1.4�����Widening Primitive
	 * Conversion���򣬲������� <code>fromClass</code> �������κ�����չ�ɸ�Ŀ�����͵�ԭ�����ͼ����װ�ࡣ ���磬
	 * <code>clazz</code> Ϊ <code>long</code> ����ô�������Ϳ����� <code>byte</code>��
	 * <code>short</code>��<code>int</code>��<code>long</code>��<code>char</code>
	 * �����װ�� <code>java.lang.Byte</code>��<code>java.lang.Short</code>��
	 * <code>java.lang.Integer</code>�� <code>java.lang.Long</code> ��
	 * <code>java.lang.Character</code> �������������������򷵻� <code>true</code>��</li>
	 * <li>���������������������򷵻� <code>false</code>��</li>
	 * </ol>
	 * </p>
	 * 
	 * @param clazz
	 *            Ŀ�����ͣ������ <code>null</code> ���Ƿ��� <code>false</code>
	 * @param fromClass
	 *            �������ͣ� <code>null</code> ��ʾ�ɸ�ֵ�������ԭ������
	 * 
	 * @return ������Ա���ֵ���򷵻� <code>null</code>
	 */
	public static boolean isAssignable(Class clazz, Class fromClass) {
		if (clazz == null) {
			return false;
		}

		// ���fromClass��null��ֻҪclazz����ԭ��������int����һ�����Ը�ֵ
		if (fromClass == null) {
			return !clazz.isPrimitive();
		}

		// �������ͬ���и��ӹ�ϵ����Ȼ���Ը�ֵ
		if (clazz.isAssignableFrom(fromClass)) {
			return true;
		}

		// ����ԭ�����ͣ�����JLS�Ĺ��������չ
		// Ŀ��classΪԭ������ʱ��fromClass����Ϊԭ�����ͺ�ԭ�����͵İ�װ���͡�
		if (clazz.isPrimitive()) {
			// boolean���Խ��ܣ�boolean
			if (Boolean.TYPE.equals(clazz)) {
				return Boolean.class.equals(fromClass);
			}

			// byte���Խ��ܣ�byte
			if (Byte.TYPE.equals(clazz)) {
				return Byte.class.equals(fromClass);
			}

			// char���Խ��ܣ�char
			if (Character.TYPE.equals(clazz)) {
				return Character.class.equals(fromClass);
			}

			// short���Խ��ܣ�short, byte
			if (Short.TYPE.equals(clazz)) {
				return Short.class.equals(fromClass) || Byte.TYPE.equals(fromClass) || Byte.class.equals(fromClass);
			}

			// int���Խ��ܣ�int��byte��short��char
			if (Integer.TYPE.equals(clazz)) {
				return Integer.class.equals(fromClass) || Byte.TYPE.equals(fromClass) || Byte.class.equals(fromClass) || Short.TYPE.equals(fromClass)
						|| Short.class.equals(fromClass) || Character.TYPE.equals(fromClass) || Character.class.equals((fromClass));
			}

			// long���Խ��ܣ�long��int��byte��short��char
			if (Long.TYPE.equals(clazz)) {
				return Long.class.equals(fromClass) || Integer.TYPE.equals(fromClass) || Integer.class.equals(fromClass) || Byte.TYPE.equals(fromClass)
						|| Byte.class.equals(fromClass) || Short.TYPE.equals(fromClass) || Short.class.equals(fromClass) || Character.TYPE.equals(fromClass)
						|| Character.class.equals((fromClass));
			}

			// float���Խ��ܣ�float, long, int, byte, short, char
			if (Float.TYPE.equals(clazz)) {
				return Float.class.equals(fromClass) || Long.TYPE.equals(fromClass) || Long.class.equals(fromClass) || Integer.TYPE.equals(fromClass)
						|| Integer.class.equals(fromClass) || Byte.TYPE.equals(fromClass) || Byte.class.equals(fromClass) || Short.TYPE.equals(fromClass)
						|| Short.class.equals(fromClass) || Character.TYPE.equals(fromClass) || Character.class.equals((fromClass));
			}

			// double���Խ��ܣ�double, float, long, int, byte, short, char
			if (Double.TYPE.equals(clazz)) {
				return Double.class.equals(fromClass) || Float.TYPE.equals(fromClass) || Float.class.equals(fromClass) || Long.TYPE.equals(fromClass)
						|| Long.class.equals(fromClass) || Integer.TYPE.equals(fromClass) || Integer.class.equals(fromClass) || Byte.TYPE.equals(fromClass)
						|| Byte.class.equals(fromClass) || Short.TYPE.equals(fromClass) || Short.class.equals(fromClass) || Character.TYPE.equals(fromClass)
						|| Character.class.equals((fromClass));
			}
		}

		return false;
	}

	/**
	 * ȡ��ָ����� <code>TypeInfo</code>��
	 * 
	 * @param type
	 *            ָ�����ӿ�
	 * 
	 * @return <code>TypeInfo</code> ����.
	 */
	protected static TypeInfo getTypeInfo(Class type) {
		if (type == null) {
			throw new IllegalArgumentException("Parameter clazz should not be null");
		}

		TypeInfo classInfo;

		synchronized (TYPE_MAP) {
			classInfo = (TypeInfo) TYPE_MAP.get(type);

			if (classInfo == null) {
				classInfo = new TypeInfo(type);
				TYPE_MAP.put(type, classInfo);
			}
		}

		return classInfo;
	}

	/**
	 * �õ���ķ���
	 * 
	 * @param clasz
	 * @return
	 */
	public static List<Class<?>> getGenericClass(Class<?> clasz) {
		List<Class<?>> returnList = new ArrayList<Class<?>>();
		Type genType = clasz.getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		for (Type param : params) {
			returnList.add((Class<?>) param);
		}
		return returnList;
	}

	/**
	 * ����һ�������Ϣ, ��������, �ӿ�, �����ά����.
	 */
	protected static class TypeInfo {
		private Class type;
		private Class componentType;
		private int dimension;
		private List superclasses = new ArrayList(2);
		private List interfaces = new ArrayList(2);

		/**
		 * ���� <code>TypeInfo</code>��
		 * 
		 * @param type
		 *            ����ָ����� <code>TypeInfo</code>
		 */
		private TypeInfo(Class type) {
			this.type = type;

			// �����array, ����componentType��dimension
			Class componentType = null;

			if (type.isArray()) {
				componentType = type;

				do {
					componentType = componentType.getComponentType();
					dimension++;
				} while (componentType.isArray());
			}

			this.componentType = componentType;

			// ȡ������superclass
			if (dimension > 0) {
				// ��primitive����ת���ɶ�Ӧ�İ�װ��
				componentType = getNonPrimitiveType(componentType);

				Class superComponentType = componentType.getSuperclass();

				// �����primitive, interface, �����������ΪObject.
				if ((superComponentType == null) && !Object.class.equals(componentType)) {
					superComponentType = Object.class;
				}

				if (superComponentType != null) {
					Class superclass = getArrayClass(superComponentType, dimension);

					superclasses.add(superclass);
					superclasses.addAll(getTypeInfo(superclass).superclasses);
				} else {
					for (int i = dimension - 1; i >= 0; i--) {
						superclasses.add(getArrayClass(Object.class, i));
					}
				}
			} else {
				// ��primitive����ת���ɶ�Ӧ�İ�װ��
				type = getNonPrimitiveType(type);

				Class superclass = type.getSuperclass();

				if (superclass != null) {
					superclasses.add(superclass);
					superclasses.addAll(getTypeInfo(superclass).superclasses);
				}
			}

			// ȡ������interface
			if (dimension == 0) {
				Class[] typeInterfaces = type.getInterfaces();
				List set = new ArrayList();

				for (int i = 0; i < typeInterfaces.length; i++) {
					Class typeInterface = typeInterfaces[i];

					set.add(typeInterface);
					set.addAll(getTypeInfo(typeInterface).interfaces);
				}

				for (Iterator i = superclasses.iterator(); i.hasNext();) {
					Class typeInterface = (Class) i.next();

					set.addAll(getTypeInfo(typeInterface).interfaces);
				}

				for (Iterator i = set.iterator(); i.hasNext();) {
					Class interfaceClass = (Class) i.next();

					if (!interfaces.contains(interfaceClass)) {
						interfaces.add(interfaceClass);
					}
				}
			} else {
				for (Iterator i = getTypeInfo(componentType).interfaces.iterator(); i.hasNext();) {
					Class componentInterface = (Class) i.next();

					interfaces.add(getArrayClass(componentInterface, dimension));
				}
			}
		}

		/**
		 * �����е�ԭ������ת���ɶ�Ӧ�İ�װ�࣬�������Ͳ��䡣
		 * 
		 * @param type
		 *            Ҫת��������
		 * 
		 * @return ��ԭ������
		 */
		private Class getNonPrimitiveType(Class type) {
			if (type.isPrimitive()) {
				if (Integer.TYPE.equals(type)) {
					type = Integer.class;
				} else if (Long.TYPE.equals(type)) {
					type = Long.class;
				} else if (Short.TYPE.equals(type)) {
					type = Short.class;
				} else if (Byte.TYPE.equals(type)) {
					type = Byte.class;
				} else if (Float.TYPE.equals(type)) {
					type = Float.class;
				} else if (Double.TYPE.equals(type)) {
					type = Double.class;
				} else if (Boolean.TYPE.equals(type)) {
					type = Boolean.class;
				} else if (Character.TYPE.equals(type)) {
					type = Character.class;
				}
			}

			return type;
		}

		/**
		 * ȡ�� <code>TypeInfo</code> �������java�ࡣ
		 * 
		 * @return <code>TypeInfo</code> �������java��
		 */
		public Class getType() {
			return type;
		}

		/**
		 * ȡ������Ԫ�ص����͡�
		 * 
		 * @return ���������, �򷵻�����Ԫ�ص�����, ���򷵻� <code>null</code>
		 */
		public Class getArrayComponentType() {
			return componentType;
		}

		/**
		 * ȡ�������ά����
		 * 
		 * @return �����ά��. �����������, �򷵻� <code>0</code>
		 */
		public int getArrayDimension() {
			return dimension;
		}

		/**
		 * ȡ�����еĸ��ࡣ
		 * 
		 * @return ���еĸ���
		 */
		public List getSuperclasses() {
			return Collections.unmodifiableList(superclasses);
		}

		/**
		 * ȡ�����еĽӿڡ�
		 * 
		 * @return ���еĽӿ�
		 */
		public List getInterfaces() {
			return Collections.unmodifiableList(interfaces);
		}
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �й�primitive���͵ķ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ����ָ����������Ӧ��primitive���͡�
	 * 
	 * @param clazz
	 *            Ҫ��������
	 * 
	 * @return ���ָ������Ϊ<code>null</code>����primitive���͵İ�װ�࣬�򷵻�<code>null</code>
	 *         �����򷵻���Ӧ��primitive���͡�
	 */
	public static Class getPrimitiveType(Class clazz) {
		if (clazz == null) {
			return null;
		}

		if (clazz.isPrimitive()) {
			return clazz;
		}

		if (clazz.equals(Long.class)) {
			return long.class;
		}

		if (clazz.equals(Integer.class)) {
			return int.class;
		}

		if (clazz.equals(Short.class)) {
			return short.class;
		}

		if (clazz.equals(Byte.class)) {
			return byte.class;
		}

		if (clazz.equals(Double.class)) {
			return double.class;
		}

		if (clazz.equals(Float.class)) {
			return float.class;
		}

		if (clazz.equals(Boolean.class)) {
			return boolean.class;
		}

		if (clazz.equals(Character.class)) {
			return char.class;
		}

		return null;
	}

	/**
	 * ����ָ����������Ӧ�ķ�primitive���͡�
	 * 
	 * @param clazz
	 *            Ҫ��������
	 * 
	 * @return ���ָ������Ϊ<code>null</code>���򷵻�<code>null</code>
	 *         �������primitive���ͣ��򷵻���Ӧ�İ�װ�࣬���򷵻�ԭʼ�����͡�
	 */
	public static Class getNonPrimitiveType(Class clazz) {
		if (clazz == null) {
			return null;
		}

		if (!clazz.isPrimitive()) {
			return clazz;
		}

		if (clazz.equals(long.class)) {
			return Long.class;
		}

		if (clazz.equals(int.class)) {
			return Integer.class;
		}

		if (clazz.equals(short.class)) {
			return Short.class;
		}

		if (clazz.equals(byte.class)) {
			return Byte.class;
		}

		if (clazz.equals(double.class)) {
			return Double.class;
		}

		if (clazz.equals(float.class)) {
			return Float.class;
		}

		if (clazz.equals(boolean.class)) {
			return Boolean.class;
		}

		if (clazz.equals(char.class)) {
			return Character.class;
		}

		return null;
	}

	public static Class[] getClasses(String packageName) {
		return getClasses(packageName, NULL_CLASS_FILTER);
	}

	/**
	 * ��ȡ���µ�������
	 * 
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class[] getClasses(String packageName, ClassFilter classFilter) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = null;
		try {
			resources = classLoader.getResources(path);
		} catch (Exception e) {
			logger.error("������Դ�쳣��path��" + path, e);
			return null;
		}
		List<File> dirs = new ArrayList<File>();
		ArrayList<Class> classes = new ArrayList<Class>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			String p = "";
			if (resource.getFile().indexOf("!") >= 0) {// ��������jar�ļ���
				p = resource.getFile().substring(0, resource.getFile().indexOf("!")).replaceAll("%20", "");
			} else {// ��classesĿ¼��
				p = resource.getFile();
			}
			if (p.startsWith("file:/"))
				p = p.substring(6);
			if (p.toLowerCase().endsWith(".jar")) {
				if (!SystemUtils.IS_OS_WINDOWS) {
					p = "/" + p;
				}
				JarFile jarFile = null;

				try {
					jarFile = new JarFile(p);
				} catch (Exception e) {
					logger.error("����jar�ļ��쳣��path��" + p, e);
					continue;
				}
				Enumeration<JarEntry> enums = jarFile.entries();
				while (enums.hasMoreElements()) {
					JarEntry entry = (JarEntry) enums.nextElement();
					String n = entry.getName();
					if (n.endsWith(".class")) {
						n = n.replaceAll("/", ".").substring(0, n.length() - 6);
						if (n.startsWith(packageName) && classFilter.accept(n)) {
							try {
								classes.add(Class.forName(n));
							} catch (Exception e) {
								logger.error("�������쳣��" + n, e);
								continue;
							}

						}
					}

				}
			} else {
				dirs.add(new File(p));
			}

		}

		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	public static interface ClassFilter {
		public boolean accept(String className);
	}

	private static final ClassFilter NULL_CLASS_FILTER = new ClassFilter() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.bench.common.lang.ClassUtils.ClassFilter#accept(java.lang.String)
		 */
		@Override
		public boolean accept(String className) {
			// TODO Auto-generated method stub
			return true;
		}

	};

	public static List<Class> findClasses(File directory, String packageName) {
		return findClasses(directory, packageName, NULL_CLASS_FILTER);
	}

	/**
	 * ����һ���ļ����µ��ļ�
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class> findClasses(File directory, String packageName, ClassFilter classFilter) {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
				try {
					if (classFilter.accept(className))
						classes.add(Class.forName(className));
				} catch (Exception e) {
					logger.error("�޷��ҵ��ࣺ" + className);
					continue;
				}
			}
		}
		return classes;
	}

	/**
	 * Similar to {@link Class#forName(java.lang.String)}, but attempts to load
	 * through the thread context class loader. Only if thread context class
	 * loader is inaccessible, or it can't find the class will it attempt to
	 * fall back to the class loader that loads the FreeMarker classes.
	 */
	public static Class forName(String className) throws ClassNotFoundException {
		try {
			return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
		} catch (ClassNotFoundException e) {
			;// Intentionally ignored
		} catch (SecurityException e) {
			;// Intentionally ignored
		}
		// Fall back to default class loader
		return Class.forName(className);
	}

	/**
	 * �ӿں�ʵ�����Ƿ���һ��,������DefaultXXX����XXXImpl
	 * 
	 * @param implementClass
	 * @param implementsInterface
	 * @return
	 */
	public static boolean isImplementSuit(Class<?> clasz, Class<?> interfaze) {
		return clasz.getSimpleName().equals(interfaze.getSimpleName() + "Impl") || clasz.getSimpleName().equals("Default" + interfaze.getSimpleName());
	}

	/**
	 * ����ʵ���࣬���ط����׵Ľӿ�,������DefaultXXX����XXXImpl������XXXX
	 * 
	 * @param clasz
	 * @return
	 */
	public static Class<?> getImplementSuitInterface(Class<?> clasz) {
		Class<?> interfaceClass = null;
		if (clasz.getInterfaces().length > 0) {
			for (Class<?> implementsInterface : clasz.getInterfaces()) {
				if (ClassUtils.isImplementSuit(clasz, implementsInterface)) {
					interfaceClass = implementsInterface;
					break;
				}
			}
		}
		return interfaceClass;
	}

	/**
	 * ��ȡ��ǰ��̳еĽӿڵ�ע��
	 * 
	 * @param field
	 * @return
	 */
	public static <T extends Annotation> T getInterfaceAnnotation(Class<?> clasz, Class<T> annotationClass) {
		for (Class<?> interfaceClass : clasz.getInterfaces()) {
			T anno = interfaceClass.getAnnotation(annotationClass);
			if (anno != null) {
				return anno;
			}
		}
		return null;
	}

	/**
	 * ��ȡ��ǰ��̳еĽӿڵ�ע��
	 * 
	 * @param field
	 * @return
	 */
	public static Class<?> getInterfaceContainsAnnotation(Class<?> clasz, Class<? extends Annotation> annotationClass) {
		for (Class<?> interfaceClass : clasz.getInterfaces()) {
			Annotation anno = interfaceClass.getAnnotation(annotationClass);
			if (anno != null) {
				return interfaceClass;
			}
		}
		return null;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param classList
	 * @return
	 */
	public static List<String> getClassName(Collection<Class<?>> classList) {
		if (classList == null) {
			return null;
		}
		List<String> nameList = new ArrayList<String>();
		for (Class<?> clasz : classList) {
			nameList.add(clasz.getName());
		}
		return nameList;
	}

	/**
	 * ��ȡ����class
	 * 
	 * @param clasz
	 * @param annotaionClass
	 * @return
	 */
	public static <T> Class<T> getParameterizedType(Class<?> clasz, Class<T> annotaionClass) {
		List<Type> superTypeList = new ArrayList<Type>();
		Type genericSuperClass = clasz.getGenericSuperclass();
		if (genericSuperClass != null) {
			superTypeList.add(genericSuperClass);
		}
		if (clasz.getGenericInterfaces() != null) {
			CollectionUtils.addAll(superTypeList, clasz.getGenericInterfaces());
		}
		for (Type superType : superTypeList) {
			if (superType instanceof ParameterizedType) {
				ParameterizedType type = (ParameterizedType) superType;
				Type[] actualTypeArguments = type.getActualTypeArguments();
				if (actualTypeArguments == null) {
					continue;
				}
				for (Type actualTypeArgument : actualTypeArguments) {
					if (!(actualTypeArgument instanceof Class)) {
						continue;
					}
					Class<?> actualTypeArgumentClass = (Class) actualTypeArgument;
					if (annotaionClass.isAssignableFrom(actualTypeArgumentClass)) {
						return (Class<T>) actualTypeArgumentClass;
					}
				}
				if (type.getRawType() != null && type.getRawType() instanceof Class) {
					Class<?> findClass = getParameterizedType((Class) type.getRawType(), annotaionClass);
					if (findClass != null && annotaionClass.isAssignableFrom(findClass)) {
						return (Class<T>) findClass;
					}
				}
			}
			if (superType instanceof Class) {
				Class<?> findClass = getParameterizedType((Class<?>) superType, annotaionClass);
				if (findClass != null && annotaionClass.isAssignableFrom(findClass)) {
					return (Class<T>) findClass;
				}
			}
		}
		return null;
	}


}

/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: CollectionUtils.java, v 0.1 2011-9-24 ����07:20:58 chenbug Exp $
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

	public static final CollectionUtils INSTANCE = new CollectionUtils();

	/**
	 * @param object
	 * @return
	 */
	public static int size(Object object) {
		if (object == null)
			return 0;
		return org.apache.commons.collections.CollectionUtils.size(object);
	}

	/**
	 * ����
	 * 
	 * @param list
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Collections.sort(list);
	}

	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		Collections.sort(list, c);
	}

	/**
	 * ͳ�Ƹ�������Ԫ���ۼ��ظ�����
	 * 
	 * @param collection
	 * @return
	 */
	@SafeVarargs
	public static <T> Map<T, Integer> getRepeatCount(Collection<T>... collections) {
		Map<T, Integer> returnMap = new HashMap<T, Integer>();
		for (Collection<T> collection : collections) {
			if (collection == null) {
				continue;
			}
			for (T t : collection) {
				if (returnMap.containsKey(t)) {
					returnMap.put(t, returnMap.get(t) + 1);
				} else {
					returnMap.put(t, 1);
				}
			}
		}
		return returnMap;
	}

	/**
	 * @param set
	 * @param obj
	 */
	public static void removeAllElement(Collection<?> set, Object obj) {
		if (set == null) {
			return;
		}
		while (set.contains(obj)) {
			set.remove(obj);
		}
	}

	/**
	 * �Ƴ����ַ���
	 * 
	 * @param collections
	 */
	public static void removeEmptyString(Collection<String> collections) {
		List<String> removeStringList = new ArrayList<String>();
		for (String string : collections) {
			if (StringUtils.isEmpty(string)) {
				removeStringList.add(string);
			}
		}
		collections.removeAll(removeStringList);
	}

	/**
	 * ȡ��С�Ķ���
	 * 
	 * @param collections
	 */
	public static <T extends Comparable<? super T>> List<T> min(Collection<T> collections) {
		T min = null;
		for (T e : collections) {
			if (min == null) {
				min = e;
			}
			if (e.compareTo(min) < 0) {
				min = e;
			}
		}
		return equal(collections, min);
	}

	/**
	 * ȡ���Ķ���
	 * 
	 * @param collections
	 */
	public static <T extends Comparable<? super T>> List<T> max(Collection<T> collections) {
		T max = null;
		for (T e : collections) {
			if (max == null) {
				max = e;
			}
			if (e.compareTo(max) > 0) {
				max = e;
			}
		}
		return equal(collections, max);
	}

	/**
	 * ȡ���ֵ
	 * 
	 * @param <T>
	 * @param collections
	 * @param object
	 * @return
	 */
	public static <T extends Comparable<? super T>> List<T> equal(Collection<T> collections, T object) {
		List<T> returnList = new ArrayList<T>();
		for (T e : collections) {
			if (e.compareTo(object) == 0) {
				returnList.add(e);
			}

		}
		return returnList;
	}

	/**
	 * coll1�Ƿ�ȫ����coll2��ֵ
	 * 
	 * @param coll1
	 * @param coll2
	 * @return
	 */
	public static <T> boolean containsAll(final Collection<T> coll1, final Collection<T> coll2) {
		// sizeΪ0
		if (coll1.size() <= 0 || coll2.size() <= 0)
			return false;

		for (T t : coll2) {
			// ֻҪ��һ�����������򷵻�false
			if (!coll1.contains(t)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ��list��ת����
	 * 
	 * @param list
	 */
	public static void reverse(List<?> list) {
		if (list == null) {
			return;
		}
		Collections.reverse(list);
	}

	/**
	 * coll2�е�Ԫ����coll1�г��ֵĸ���
	 * 
	 * @param coll1
	 * @param coll2
	 * @return
	 */
	public static <T> int countMatchEach(final Collection<T> coll1, final Collection<T> coll2) {
		// sizeΪ0
		if (coll1.size() <= 0 || coll2.size() <= 0)
			return 0;

		int count = 0;
		for (T t : coll2) {
			// ��������1
			if (coll1.contains(t)) {
				count++;
			}
		}
		return count;
	}
}

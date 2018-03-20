/**
 * benchcode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.bag.HashBag;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: ListUtils.java, v 0.1 2010-5-31 ����05:34:09 chenbug Exp $
 */
public class ListUtils extends org.apache.commons.collections.ListUtils {

	public static final ListUtils INSTANCE = new ListUtils();

	/**
	 * ȡ���ϲ���Ԫ��
	 * 
	 * @param list
	 * @param fromIndex
	 * @param endIndex
	 * @return
	 */
	public static <T> List<T> subList(List<T> list, int fromIndex, int endIndex) {
		List<T> returnList = new ArrayList<T>();
		for (int i = fromIndex; i < endIndex && i < list.size(); i++) {
			returnList.add(list.get(i));
		}
		return returnList;
	}

	public static <E> List<E> subtract2(final List<E> list1, final List<? extends E> list2) {
		final ArrayList<E> result = new ArrayList<E>();
		final HashBag<E> bag = new HashBag<E>(list2);
		for (final E e : list1) {
			if (!bag.remove(e, 1)) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * �Ƴ���ȵ�,ֻ����1��
	 * 
	 * @param list
	 * @param list2
	 */
	public static <T> List<T> removeEqual(List<T> list) {
		Set<T> set = new HashSet<T>(list);
		return new ArrayList<T>(set);
	}

	/**
	 * ������в���ȵ�,��List1���뵽List2
	 * 
	 * @param list
	 * @param list2
	 */
	public static <T> List<T> addUnequalAll(List<T> list1, List<T> list2) {
		if (ListUtils.size(list1) == 0) {
			return list2;
		}
		for (T t : list1) {
			if (!list2.contains(t)) {
				list2.add(t);
			}
		}
		return list2;
	}

	/**
	 * @param list
	 * @return
	 */
	public static <T> List<T> clone(List<T> list) {
		List<T> retList = new ArrayList<T>();
		retList.addAll(list);
		return retList;
	}

	/**
	 * ����һ��������
	 * 
	 * @return
	 */
	public static final List<?> newList() {
		return new ArrayList<Object>();
	}

	/**
	 * ����һ���¼���
	 * 
	 * @return
	 */
	public static final <T> List<T> newList(Collection<T> collections) {
		return new ArrayList<T>(collections);
	}


	/**
	 * Listת��������
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Class<T> clasz, List<T> list) {
		return list.toArray((T[]) Array.newInstance(clasz, list.size()));
	}

	/**
	 * Ϊ��
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		return size(list) == 0;
	}

	/**
	 * �ǿ�
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list) {
		return size(list) > 0;
	}

	public static <T> List<T> toList(T... ts) {
		if (ts == null) {
			return new ArrayList<T>(0);
		}
		List<T> retList = new ArrayList<T>();
		CollectionUtils.addAll(retList, ts);
		return retList;
	}

	/**
	 * ����list����
	 * 
	 * @param list
	 * @return
	 */
	public static int size(List<?> list) {
		return list == null ? 0 : list.size();
	}

	/**
	 * ת��Ϊ�ַ���List
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<String> toStringList(List<T> list) {
		if (list == null) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();
		for (T t : list) {
			if (t == null) {
				returnList.add(null);
			} else {
				returnList.add(ObjectUtils.toString(t));
			}
		}
		return returnList;

	}

	/**
	 * ת��ΪLong List
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<Long> toLong(List<T> list) {
		if (list == null) {
			return null;
		}
		List<Long> returnList = new ArrayList<Long>();
		for (T t : list) {
			if (t == null) {
				returnList.add(null);
			} else if (t instanceof Number) {
				returnList.add(((Number) t).longValue());
			} else if (t instanceof String) {
				returnList.add(Long.parseLong((String) t));
			} else {
				returnList.add(Long.parseLong(t.toString()));
			}
		}
		return returnList;
	}

	/**
	 * ת��ΪInteger List
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<Integer> toInteger(List<T> list) {
		if (list == null) {
			return null;
		}
		List<Integer> returnList = new ArrayList<Integer>();
		for (T t : list) {
			if (t == null) {
				returnList.add(null);
			} else if (t instanceof Number) {
				returnList.add(((Number) t).intValue());
			} else if (t instanceof String) {
				returnList.add(Integer.parseInt((String) t));
			} else {
				returnList.add(Integer.parseInt(t.toString()));
			}
		}
		return returnList;
	}

	/**
	 * ת��ΪInteger List
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<Double> toDouble(List<T> list) {
		if (list == null) {
			return null;
		}
		List<Double> returnList = new ArrayList<Double>();
		for (T t : list) {
			if (t == null) {
				returnList.add(null);
			} else if (t instanceof Number) {
				returnList.add(((Number) t).doubleValue());
			} else if (t instanceof String) {
				returnList.add(Double.parseDouble((String) t));
			} else {
				returnList.add(Double.parseDouble(t.toString()));
			}
		}
		return returnList;
	}

	/**
	 * �Ƿ����
	 * 
	 * @param list
	 * @param t
	 * @return
	 */
	public static <T> boolean contains(List<T> list, T t) {
		if (list == null) {
			return false;
		}
		return list.contains(t);
	}

	/**
	 * �Ƿ����
	 * 
	 * @param list
	 * @param t
	 * @return
	 */
	public static <T> boolean containsAny(List<T> list, T... ts) {
		if (list == null) {
			return false;
		}
		if (ts == null) {
			return false;
		}
		for (T t : ts) {
			if (list.contains(t)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * list1�Ƿ��������һ��list2�ڵ�Ԫ��
	 * 
	 * @param list
	 * @param t
	 * @return
	 */
	public static <T> boolean containsAny(List<T> list, List<T> list2) {
		if (list == null) {
			return false;
		}
		if (list2 == null) {
			return false;
		}
		for (T t : list2) {
			if (list.contains(t)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * list2��Ԫ���Ƿ�ȫ��������list����
	 * 
	 * @param list
	 * @param list2
	 * @return
	 */
	public static <T> boolean containsAll(List<T> list, List<T> list2) {
		if (list == null || isEmpty(list)) {
			return false;
		}
		if (list2 == null) {
			return false;
		}
		for (T t : list2) {
			if (!list.contains(t)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ����collection��retain��ͬ���е�Ԫ��
	 * 
	 * @param collection
	 * @param retain
	 * @return
	 */
	public static <T> List<T> retainAll2(Collection<T> collection, Collection<T> retain) {
		List<T> list = new ArrayList<T>(Math.min(collection.size(), retain.size()));

		for (Iterator<T> iter = collection.iterator(); iter.hasNext();) {
			T obj = iter.next();
			if (retain.contains(obj)) {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * ����һ�����飬����Ϊlength��ÿ��Ԫ��Ĭ��ֵΪdefaultValue
	 * 
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static List<Integer> createIntSequenceList(int length) {
		List<Integer> returnList = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			returnList.add(i);
		}
		return returnList;
	}

	/**
	 * ����һ�����飬����Ϊlength��ÿ��Ԫ��Ĭ��ֵΪdefaultValue
	 * 
	 * @param length
	 * @param defaultValue
	 * @return
	 */
	public static <T> List<T> createList(int length, T defaultValue) {
		List<T> returnList = new ArrayList<T>();
		for (int i = 0; i < length; i++) {
			returnList.add(defaultValue);
		}
		return returnList;
	}

	/**
	 * �Ӽ������Ƴ�����Ķ���
	 * 
	 * @param list
	 * @param ifReject
	 * @return
	 */
	public static <T> List<T> removeReject(List<T> list, IfReject<T> ifReject) {
		List<T> returnList = new ArrayList<T>(list);
		// ȥ������
		for (int i = 0; i < returnList.size(); i++) {
			T consultT = returnList.get(i);
			// ��ɾ���ļ���
			List<T> currentRemoveList = new ArrayList<T>();
			for (int j = i; j < returnList.size(); j++) {
				T testT = returnList.get(j);
				if (ifReject.reject(consultT, testT)) {
					currentRemoveList.add(testT);
				}
			}
			returnList.removeAll(currentRemoveList);
		}
		return returnList;
	}

	public static interface IfReject<T> {

		public boolean reject(T t1, T t2);
	}
}

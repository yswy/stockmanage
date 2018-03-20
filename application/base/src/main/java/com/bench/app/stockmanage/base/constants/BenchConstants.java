package com.bench.app.stockmanage.base.constants;

/**
 * bench常量
 * 
 * @author chenbug
 *
 * @version $Id: BenchConstatns.java, v 0.1 2018年2月26日 下午5:28:45 chenbug Exp $
 */
public class BenchConstants {

	/** Resource-Filter 属性，用于是否使用velocity作为资源的过滤器 */
	public final static String MANIFEST_RESOURCE_FILTER = "Resource-Filter";

	/**
	 * bench组件名称
	 */
	public final static String MANIFEST_COMPONENT_NAME = "Bench-Component-Name";

	/** 链接符号 */
	public final static String LINK_SYMBOL = "$";
	/** 链接符号 */
	public final static String LINK_PROTOCAL = ":";

	/** spring 扩展点 bean 在nuxeo组件工厂中的名称前缀 */
	public final static String TYPE_EXTENSION_POINT_VALUE = "virtual_cp_ep";
	public final static String TYPE_EXTENSION_POINT = TYPE_EXTENSION_POINT_VALUE + LINK_PROTOCAL;

	/** spring 扩展 bean 在nuxeo组件工厂中的名称前缀 */
	public final static String TYPE_EXTENSION_VALUE = "virtual_cp_e";
	public final static String TYPE_EXTENSION = TYPE_EXTENSION_VALUE + LINK_PROTOCAL;
}

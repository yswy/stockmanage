package com.bench.app.stockmanage.base.constants;

/**
 * bench����
 * 
 * @author chenbug
 *
 * @version $Id: BenchConstatns.java, v 0.1 2018��2��26�� ����5:28:45 chenbug Exp $
 */
public class BenchConstants {

	/** Resource-Filter ���ԣ������Ƿ�ʹ��velocity��Ϊ��Դ�Ĺ����� */
	public final static String MANIFEST_RESOURCE_FILTER = "Resource-Filter";

	/**
	 * bench�������
	 */
	public final static String MANIFEST_COMPONENT_NAME = "Bench-Component-Name";

	/** ���ӷ��� */
	public final static String LINK_SYMBOL = "$";
	/** ���ӷ��� */
	public final static String LINK_PROTOCAL = ":";

	/** spring ��չ�� bean ��nuxeo��������е�����ǰ׺ */
	public final static String TYPE_EXTENSION_POINT_VALUE = "virtual_cp_ep";
	public final static String TYPE_EXTENSION_POINT = TYPE_EXTENSION_POINT_VALUE + LINK_PROTOCAL;

	/** spring ��չ bean ��nuxeo��������е�����ǰ׺ */
	public final static String TYPE_EXTENSION_VALUE = "virtual_cp_e";
	public final static String TYPE_EXTENSION = TYPE_EXTENSION_VALUE + LINK_PROTOCAL;
}

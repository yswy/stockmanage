package com.bench.app.stockmanage.base.config;

/**
 * ���ݿ�����
 * 
 * @author chenbug
 *
 * @version $Id: RemoteDBConfigration.java, v 0.1 2016��11��19�� ����11:25:41 chenbug
 *          Exp $
 */
public class RemoteDBConfigration extends DBConfigration {

	/**
	 * Զ��url
	 */
	private String remoteUrl;

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
}

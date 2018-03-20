package com.bench.app.stockmanage.base.config;

/**
 * 数据库配置
 * 
 * @author chenbug
 *
 * @version $Id: RemoteDBConfigration.java, v 0.1 2016年11月19日 下午11:25:41 chenbug
 *          Exp $
 */
public class RemoteDBConfigration extends DBConfigration {

	/**
	 * 远程url
	 */
	private String remoteUrl;

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
}

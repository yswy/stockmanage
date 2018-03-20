package com.bench.app.stockmanage.base.config;

import java.io.File;
import java.util.Date;

import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.bench.app.stockmanage.base.utils.DateUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;
import com.bench.app.stockmanage.base.utils.SystemUtils;

/**
 * bench系统配置
 * 
 * @author chenbug
 * 
 * @version $Id: BenchSystemConfigration.java, v 0.1 2012-12-14 下午5:17:36
 *          chenbug Exp $
 */
public class BenchSourceCodeConfigration {

	/**
	 * 得到源码版本
	 * 
	 * @return
	 */
	public static final long getSourceVersion() {
		String sourceVersion = ConfigrationFactory.getConfigration().getPropertyValue("source_version");
		sourceVersion = StringUtils.trim(sourceVersion);
		if (StringUtils.isEmpty(sourceVersion) && SystemUtils.getOsInfo().isWindows()) {
			try {
				String sourcePath = getWindowsSourcePath();
				DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
				SVNClientManager svnClientManager = SVNClientManager.newInstance(options);
				SVNStatus svnStatus = svnClientManager.getStatusClient().doStatus(new File(sourcePath), false);
				return svnStatus.getCommittedRevision().getNumber();
			} catch (Exception e) {
				return -1;
			}
		}
		if (StringUtils.isEmpty(sourceVersion)) {
			throw new RuntimeException("无法找到源码版本");
		}
		return Integer.parseInt(sourceVersion);
	}

	/**
	 * 得到windows源码路径
	 * 
	 * @return
	 */
	public static String getWindowsSourcePath() {
		// 输出源码路径
		File sourceFilePath = new File(System.getProperty("user.dir"));
		for (int i = 0; i < 50; i++) {
			if (new File(sourceFilePath, "all.xml").exists()) {
				break;
			}
			sourceFilePath = sourceFilePath.getParentFile();
			if (sourceFilePath == null) {
				break;
			}
		}
		if (sourceFilePath != null) {
			return sourceFilePath.getPath();
		}
		return null;
	}

	/**
	 * 得到源码路径
	 * 
	 * @return
	 */
	public static final String getSourcePath() {
		String sourcePath = ConfigrationFactory.getConfigration().getPropertyValue("source_path");
		sourcePath = StringUtils.trim(sourcePath);
		if (StringUtils.isEmpty(sourcePath) && SystemUtils.getOsInfo().isWindows()) {
			sourcePath = getWindowsSourcePath();
		}
		if (StringUtils.isEmpty(sourcePath)) {
			throw new RuntimeException("无法找到源码路径");
		}
		return sourcePath;
	}

	/**
	 * 得到源码更新日期
	 * 
	 * @return
	 */
	public static final Date getSourceCommittedDate() {
		String sourceCommitedDate = ConfigrationFactory.getConfigration().getPropertyValue("source_committed_date");
		sourceCommitedDate = StringUtils.trim(sourceCommitedDate);
		if (StringUtils.isEmpty(sourceCommitedDate) && SystemUtils.getOsInfo().isWindows()) {
			try {
				String sourcePath = getWindowsSourcePath();
				DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
				SVNClientManager svnClientManager = SVNClientManager.newInstance(options);
				SVNStatus svnStatus = svnClientManager.getStatusClient().doStatus(new File(sourcePath), false);
				return svnStatus.getCommittedDate();
			} catch (Exception e) {
				return null;
			}
		}
		if (StringUtils.isEmpty(sourceCommitedDate)) {
			throw new RuntimeException("无法找到源码提交时间");
		}
		return DateUtils.parseDateNewFormat(sourceCommitedDate);
	}
}

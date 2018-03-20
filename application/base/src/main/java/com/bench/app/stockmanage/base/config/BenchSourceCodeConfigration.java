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
 * benchϵͳ����
 * 
 * @author chenbug
 * 
 * @version $Id: BenchSystemConfigration.java, v 0.1 2012-12-14 ����5:17:36
 *          chenbug Exp $
 */
public class BenchSourceCodeConfigration {

	/**
	 * �õ�Դ��汾
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
			throw new RuntimeException("�޷��ҵ�Դ��汾");
		}
		return Integer.parseInt(sourceVersion);
	}

	/**
	 * �õ�windowsԴ��·��
	 * 
	 * @return
	 */
	public static String getWindowsSourcePath() {
		// ���Դ��·��
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
	 * �õ�Դ��·��
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
			throw new RuntimeException("�޷��ҵ�Դ��·��");
		}
		return sourcePath;
	}

	/**
	 * �õ�Դ���������
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
			throw new RuntimeException("�޷��ҵ�Դ���ύʱ��");
		}
		return DateUtils.parseDateNewFormat(sourceCommitedDate);
	}
}

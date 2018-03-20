package com.bench.app.stockmanage.base.logging;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * ����log4j���չ�����־�����Ի�����
 * 
 * @author chenbug
 * @version $Id: DailyRollingFileAppender.java,v 0.1 2009-6-26 ����02:05:38
 *          chenbug Exp $
 */
public class DailyRollingFileAppender extends org.apache.log4j.FileAppender {

	public static final Logger log = Logger.getLogger(DailyRollingFileAppender.class);

	/** �������д��datepattern */
	private final String datePattern = "'.'yyyy-MM-dd";

	/** ����ļ��������� */
	private int maxBackupIndex = 7;

	/** "�ļ���+�ϴ�������ʱ��" */
	private String scheduledFilename;

	/**
	 * The next time we estimate a rollover should occur.
	 */
	private long nextCheck = System.currentTimeMillis() - 1;

	Date now = new Date();

	SimpleDateFormat sdf;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String, boolean,
	 * boolean, int)
	 */
	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		// TODO Auto-generated method stub
		File f = new File(fileName);
		if (f == null || !f.exists()) {
			new File(FilenameUtils.getFullPath(fileName)).mkdirs();
			f.createNewFile();
		}
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}

	/**
	 * ��ʼ����Appender�����ʱ�����һ��
	 */
	public void activateOptions() {
		super.activateOptions();
		if (fileName != null) {
			now.setTime(System.currentTimeMillis());
			sdf = new SimpleDateFormat(datePattern);
			File file = new File(fileName);
			// ��ȡ������ʱ��ƴ�ɵ��ļ���
			scheduledFilename = fileName + sdf.format(new Date(file.lastModified()));
		} else {
			throw new RuntimeException("file����û������");
		}
		if (maxBackupIndex <= 0) {
			throw new RuntimeException("maxBackupIndex��������ΪС�ڵ���0��Ĭ��Ϊ" + this.maxBackupIndex);
		}
	}

	/**
	 * �����ļ��ĺ����� 1.���ļ�������ʱ������бȽϣ�ȷ���Ƿ���� 2.if��Ҫ���£���ǰ�ļ�rename���ļ���+���ڣ� ���¿�ʼд�ļ� 3.
	 * ������õ�maxBackupIndex,ɾ�����ڵ��ļ�
	 */
	public void rollOver() throws IOException {

		String datedFilename = fileName + sdf.format(now);
		// ����ϴ�д�����ڸ���ǰ������ͬ������Ҫ���ļ�
		if (scheduledFilename.equals(datedFilename)) {
			return;
		}

		// close current file, and rename it to datedFilename
		this.closeFile();

		File target = new File(scheduledFilename);
		if (target.exists()) {
			target.delete();
		}

		File file = new File(fileName);
		boolean result = file.renameTo(target);
		if (result) {
			log.debug(fileName + " -> " + scheduledFilename);
		} else {
			log.error("Failed to rename [" + fileName + "] to [" + scheduledFilename + "].");
		}

		// ɾ�������ļ�
		if (maxBackupIndex > 0) {
			File folder = new File(file.getParent());
			List<String> maxBackupIndexDates = getMaxBackupIndexDates();
			for (File ff : folder.listFiles()) { // ����Ŀ¼�������ڲ��ڱ��ݷ�Χ�ڵ���־ɾ��
				if (ff.getName().startsWith(file.getName()) && !ff.getName().equals(file.getName())) {
					// ��ȡ�ļ�����������ʱ���
					String markedDate = ff.getName().substring(file.getName().length());
					if (!maxBackupIndexDates.contains(markedDate)) {
						try {
							if (ff.delete()) {
								log.debug(ff.getName() + " ->deleted ");
							} else {
								log.error("Failed to deleted old DayRollingFileAppender file :"
										+ ff.getName());
							}
						} catch (Exception e) {
							log.error("ɾ����־�����ļ��쳣,file=" + ff.getName(), e);
						}
					}
				}
			}
		}

		try {
			// This will also close the file. This is OK since multiple
			// close operations are safe.
			this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
		} catch (IOException e) {
			errorHandler.error("setFile(" + fileName + ", false) call failed.");
		}
		scheduledFilename = datedFilename; // �������������ڴ�
	}

	/**
	 * Actual writing occurs here. ���������д����������ִ�й��̣�
	 * */
	protected void subAppend(LoggingEvent event) {
		long n = System.currentTimeMillis();
		if (n >= nextCheck) { // ��ÿ��д����ǰ�ж�һ���Ƿ���Ҫ�����ļ�
			now.setTime(n);
			nextCheck = getNextDayCheckPoint(now);
			try {
				rollOver();
			} catch (IOException ioe) {
				LogLog.error("rollOver() failed.", ioe);
			}
		}
		super.subAppend(event);
	}

	/**
	 * ��ȡ��һ���ʱ������
	 * 
	 * @param now
	 * @return
	 */
	long getNextDayCheckPoint(Date now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);// ע��MILLISECOND,����ҲҪ��0.�����������Ҳ�Ҳ�������
		calendar.add(Calendar.DATE, 1);
		return calendar.getTimeInMillis();
	}

/** 
* ����maxBackupIndex���õı����ļ���������ȡҪ����log�ļ������ڷ�Χ���� 
* @return list<'fileName+yyyy-MM-dd'> 
*/
	List<String> getMaxBackupIndexDates() {
		List<String> result = new ArrayList<String>();
		if (maxBackupIndex > 0) {
			for (int i = 1; i <= maxBackupIndex; i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);// ע��MILLISECOND,����ҲҪ��0.�����������Ҳ�Ҳ�������
				calendar.add(Calendar.DATE, -i);
				result.add(sdf.format(calendar.getTime()));
			}
		}
		return result;
	}

	/**
	 * @param maxBackupIndex
	 *            The maxBackupIndex to set.
	 */
	public void setMaxBackupIndex(int maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}

	/**
	 * @return Returns the maxBackupIndex.
	 */
	public int getMaxBackupIndex() {
		return maxBackupIndex;
	}

}

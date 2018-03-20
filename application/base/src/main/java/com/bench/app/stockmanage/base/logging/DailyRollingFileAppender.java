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
 * 重载log4j的日滚动日志，个性化配置
 * 
 * @author chenbug
 * @version $Id: DailyRollingFileAppender.java,v 0.1 2009-6-26 下午02:05:38
 *          chenbug Exp $
 */
public class DailyRollingFileAppender extends org.apache.log4j.FileAppender {

	public static final Logger log = Logger.getLogger(DailyRollingFileAppender.class);

	/** 不允许改写的datepattern */
	private final String datePattern = "'.'yyyy-MM-dd";

	/** 最多文件增长个数 */
	private int maxBackupIndex = 7;

	/** "文件名+上次最后更新时间" */
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
	 * 初始化本Appender对象的时候调用一次
	 */
	public void activateOptions() {
		super.activateOptions();
		if (fileName != null) {
			now.setTime(System.currentTimeMillis());
			sdf = new SimpleDateFormat(datePattern);
			File file = new File(fileName);
			// 获取最后更新时间拼成的文件名
			scheduledFilename = fileName + sdf.format(new Date(file.lastModified()));
		} else {
			throw new RuntimeException("file参数没有设置");
		}
		if (maxBackupIndex <= 0) {
			throw new RuntimeException("maxBackupIndex不能设置为小于等于0，默认为" + this.maxBackupIndex);
		}
	}

	/**
	 * 滚动文件的函数： 1.对文件名带的时间戳进行比较，确定是否更新 2.if需要更新，当前文件rename到文件名+日期， 重新开始写文件 3.
	 * 针对配置的maxBackupIndex,删除过期的文件
	 */
	public void rollOver() throws IOException {

		String datedFilename = fileName + sdf.format(now);
		// 如果上次写的日期跟当前日期相同，不需要换文件
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

		// 删除过期文件
		if (maxBackupIndex > 0) {
			File folder = new File(file.getParent());
			List<String> maxBackupIndexDates = getMaxBackupIndexDates();
			for (File ff : folder.listFiles()) { // 遍历目录，将日期不在备份范围内的日志删掉
				if (ff.getName().startsWith(file.getName()) && !ff.getName().equals(file.getName())) {
					// 获取文件名带的日期时间戳
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
							log.error("删除日志过期文件异常,file=" + ff.getName(), e);
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
		scheduledFilename = datedFilename; // 更新最后更新日期戳
	}

	/**
	 * Actual writing occurs here. 这个方法是写操作真正的执行过程！
	 * */
	protected void subAppend(LoggingEvent event) {
		long n = System.currentTimeMillis();
		if (n >= nextCheck) { // 在每次写操作前判断一下是否需要滚动文件
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
	 * 获取下一天的时间变更点
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
		calendar.set(Calendar.MILLISECOND, 0);// 注意MILLISECOND,毫秒也要置0.。。否则错了也找不出来的
		calendar.add(Calendar.DATE, 1);
		return calendar.getTimeInMillis();
	}

/** 
* 根据maxBackupIndex配置的备份文件个数，获取要保留log文件的日期范围集合 
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
				calendar.set(Calendar.MILLISECOND, 0);// 注意MILLISECOND,毫秒也要置0.。。否则错了也找不出来的
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

package com.bench.app.stockmanage.base.database.oracle;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.bench.app.stockmanage.base.database.DatabaseUtils;

import oracle.sql.TIMESTAMP;

/*
 * Oracle������
 */
public class OracleUtils {

	private static final Logger log = Logger.getLogger(OracleUtils.class);

	public static final String ENV_ORACLE_HOME = "ORACLE_HOME";

	public static Date toDate(TIMESTAMP timestamp) {
		try {
			return new Date(timestamp.timestampValue().getTime());
		} catch (Exception e) {
			log.error("ת�����ڸ�ʽ�쳣,timestamp=" + timestamp, e);
			return null;
		}
	}

	/**
	 * ת��clob��String
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static String toString(Clob clob) throws SQLException {
		return DatabaseUtils.convertClobToString(clob);
	}

	/**
	 * ת��blob��bytes
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static byte[] toByte(Blob blob) throws SQLException {
		return DatabaseUtils.convertBlobToBytes(blob);
	}

}

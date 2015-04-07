package lib.database;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lib.database.sql.KeyParams;
import lib.database.sql.NullableParams;
import lib.database.sql.SqlTable;
import lib.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAO {

	private static final Logger logger = LoggerFactory.getLogger(DAO.class);

	public static Connection getConnection() {
		Connection con = null;
		String url = Setting.get("database", "url");
		String id = Setting.get("database", "id");
		String pw = Setting.get("database", "password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	Connection conn;

	public DAO() {
		conn = getConnection();
	}

	private static PreparedStatement getPSTMT(Connection conn, String sql, Object[] parameters) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.length; j++) {
					pstmt.setObject(j + 1, parameters[j]);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.debug(pstmt.toString());
		return pstmt;
	}

	public List<Object> getRecord(String sql, int resultSize, Object... parameters) {
		List<Object> record = null;
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (record == null)
					record = new ArrayList<Object>();
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return record;
	}

	public Map<String, Object> getRecordMap(String sql, Object... parameters) {
		Map<String, Object> record = null;
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (record == null)
					record = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					record.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return record;
	}

	public List<List<Object>> getRecords(String sql, int resultSize, Object... parameters) {
		List<Object> record;
		List<List<Object>> result = null;
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<List<Object>>();
				record = new ArrayList<Object>();
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
				result.add(record);
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Map<String, Object>> getRecordsMap(String sql, Object... parameters) {
		List<Map<String, Object>> result = null;
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<Map<String, Object>>();
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				result.add(columns);
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public <T> T getRecord(Class<T> cLass, String sql, Object... parameters) {
		Map<String, Object> record = getRecordMap(sql, parameters);
		T result = Parser.getObject(cLass, record);
		return result;
	}

	public static final String and = "=? and ";
	public static final String comma = "=?, ";

	public boolean fill(Object record) {
		KeyParams kp = new NullableParams(record);
		Map<String, Object> recordMap = getRecordMap(String.format("SELECT * FROM %s WHERE %s", kp.getTableName(), kp.getKeyFieldNames(and)), kp
				.getKeyParams().toArray());
		return Parser.setObject(record, recordMap);
	}

	public <T> T getRecordByClass(Class<T> cLass, Object... parameters) {
		KeyParams sp = KeyParams.getInstance(cLass);
		Map<String, Object> record = getRecordMap(String.format("SELECT * FROM %s WHERE %s", sp.getTableName(), sp.getKeyFieldNames(and)), parameters);
		T result = Parser.getObject(cLass, record);
		return result;
	}

	public <T> List<T> getRecords(Class<T> cLass, String sql, Object... parameters) {
		List<Map<String, Object>> records = getRecordsMap(sql, parameters);
		List<T> result = new ArrayList<T>();
		records.forEach(record -> {
			result.add(Parser.getObject(cLass, record));
		});
		return result;
	}

	public <T> List<T> getRecordsByClass(Class<T> cLass, String whereClause, Object... parameters) {
		List<Map<String, Object>> records = getRecordsMap(
				String.format("SELECT * FROM %s %s", SqlTable.getInstance(cLass).getTableName(), whereClause), parameters);
		List<T> result = new ArrayList<T>();
		records.forEach(record -> {
			result.add(Parser.getObject(cLass, record));
		});
		return result;
	}

	public <T> List<T> getRecordsByClass(Class<T> cLass) {
		return getRecordsByClass(cLass, "");
	}

	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			for (int j = 0; j < parameters.length; j++) {
				pstmt.setObject(j + 1, parameters[j]);
			}
			logger.debug(pstmt.toString());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			/*
			 * try에 close() 함수를 두면. exception 발생시 connection을 안전하게 종료하지 못합니다.
			 */
			close(pstmt);
		}
		return true;
	}

	private static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException sqle) {
			}
	}

	private static void close(PreparedStatement pstmt) {
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException sqle) {
			}
	}

	public void close() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException sqle) {
			}
	}

	private final static String INSERT = "INSERT %s SET %s";

	public boolean insert(Object record) {
		KeyParams sap = new KeyParams(record);
		if (sap.isEmpty())
			return false;
		String sql = String.format(INSERT, sap.getTableName(), sap.getIntegratedFieldNames(comma));
		return execute(sql, sap.getIntegratedParams().toArray());
	}

	private final static String UPDATE = "UPDATE %s SET %s WHERE %s";

	public boolean update(Object record) {
		KeyParams sap = new KeyParams(record);
		if (!sap.hasKeyParams())
			return false;
		if (!sap.hasParams())
			return false;
		String sql = String.format(UPDATE, sap.getTableName(), sap.getFieldNames(comma), sap.getKeyFieldNames(and));
		return execute(sql, sap.getIntegratedParams().toArray());
	}

	private final static String DELETE = "DELETE FROM %s WHERE %s";

	public boolean delete(Object record) {
		KeyParams sap = new KeyParams(record);
		if (!sap.hasKeyParams())
			return false;
		return execute(String.format(DELETE, sap.getTableName(), sap.getKeyFieldNames(and)), sap.getKeyParams().toArray());
	}

	private final static String LAST = "SELECT LAST_INSERT_ID();";

	public BigInteger getLastKey() {
		return (BigInteger) getRecord(LAST, 1).get(0);
	}

}

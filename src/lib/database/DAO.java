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
			logger.debug(sql);
			logger.debug(parameters.toString());
			e.printStackTrace();
		}
		return pstmt;
	}

	public List<Object> getRecord(String sql, int resultSize, Object... parameters) {
		List<Object> record = new ArrayList<Object>();
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			logger.debug(sql);
			logger.debug(parameters.toString());
			e.printStackTrace();
		}
		return record;
	}

	public Map<String, Object> getRecordMap(String sql, Object... parameters) {
		Map<String, Object> record = new LinkedHashMap<String, Object>();
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					record.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			logger.debug(sql);
			logger.debug(parameters.toString());
			e.printStackTrace();
		}
		return record;
	}

	public List<List<Object>> getRecords(String sql, int resultSize, Object... parameters) {
		List<Object> record;
		List<List<Object>> result = new ArrayList<List<Object>>();
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				record = new ArrayList<Object>();
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
				result.add(record);
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			logger.debug(sql);
			e.printStackTrace();
		}

		return result;
	}

	public List<Map<String, Object>> getRecordsMap(String sql, Object... parameters) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			PreparedStatement pstmt = getPSTMT(conn, sql, parameters);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				result.add(columns);
			}
			close(pstmt);
			close(rs);
		} catch (SQLException e) {
			logger.debug(sql);
			e.printStackTrace();
		}
		return result;
	}

	public <T> T getRecord(Class<T> cLass, String sql, Object... parameters) {
		Map<String, Object> record = getRecordMap(sql, parameters);
		T result = Parser.setObject(cLass, record);
		return result;
	}

	public <T> T getRecordByClass(Class<T> cLass, Object... parameters) {
		SqlParams sp = SqlParams.getInstance(cLass);
		Map<String, Object> record = getRecordMap(String.format("SELECT * FROM %s WHERE %s", sp.getTableName(), sp.getKeyFieldNames()), parameters);
		T result = Parser.setObject(cLass, record);
		return result;
	}

	public <T> List<T> getRecords(Class<T> cLass, String sql, Object... parameters) {
		List<Map<String, Object>> records = getRecordsMap(sql, parameters);
		List<T> result = new ArrayList<T>();
		records.forEach(record -> {
			result.add(Parser.setObject(cLass, record));
		});
		return result;
	}

	public <T> List<T> getRecordsByClass(Class<T> cLass, String whereClause, Object... parameters) {
		List<Map<String, Object>> records = getRecordsMap(
				String.format("SELECT * FROM %s %s", SqlTable.getInstance(cLass).getTableName(), whereClause), parameters);
		List<T> result = new ArrayList<T>();
		records.forEach(record -> {
			result.add(Parser.setObject(cLass, record));
		});
		return result;
	}
	
	public <T> List<T> getRecordsByClass(Class<T> cLass) {
		return getRecordsByClass(cLass, "");
	}

	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int j = 0; j < parameters.length; j++) {
				pstmt.setObject(j + 1, parameters[j]);
			}
			pstmt.execute();
			close(pstmt);
			return true;
		} catch (SQLException e) {
			logger.debug(sql);
			e.printStackTrace();
			return false;
		}
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
		SqlParams sap = new SqlParams(record);
		if (sap.isEmpty())
			return false;
		String sql = String.format(INSERT, sap.getTableName(), sap.getIntegratedFieldNames());
		return execute(sql, sap.getIntegratedParams().toArray());
	}

	private final static String UPDATE = "UPDATE %s SET %s WHERE %s";

	public boolean update(Object record) {
		SqlParams sap = new SqlParams(record);
		if (!sap.hasKeyParams())
			return false;
		if (!sap.hasParams())
			return false;
		String sql = String.format(UPDATE, sap.getTableName(), sap.getFieldNames(), sap.getKeyFieldNames());
		return execute(sql, sap.getIntegratedParams().toArray());
	}

	private final static String DELETE = "UPDATE %s SET %s WHERE %s";

	public boolean delete(Object record) {
		SqlParams sap = new SqlParams(record);
		if (!sap.hasKeyParams())
			return false;
		return execute(String.format(DELETE, sap.getTableName(), sap.getKeyFieldNames()), sap.getKeyParams().toArray());
	}

	private final static String LAST = "SELECT LAST_INSERT_ID();";

	public BigInteger getLastKey() {
		return (BigInteger) getRecord(LAST, 1).get(0);
	}
}

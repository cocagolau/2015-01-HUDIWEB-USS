package lib.database.maker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lib.database.DAO;
import lib.database.annotation.Column;
import lib.database.annotation.Exclude;
import lib.database.annotation.Key;
import lib.database.annotation.OtherTable;
import lib.database.annotation.Table;
import lib.database.sql.SqlField;
import lib.database.sql.SqlFieldNormal;
import lib.setting.Setting;

public class TableMaker {

	private DAO dao;
	private Class<?> tableClass;
	private String tableName;
	private String table_suffix;
	private String createQuery;

	public TableMaker(Class<?> tableObj) {
		dao = new DAO();
		tableClass = tableObj;
		tableName = tableClass.getSimpleName();
		table_suffix = Setting.get("database", "default", "table_suffix");
		if (!tableClass.isAnnotationPresent(Table.class))
			return;
		Table table = tableClass.getAnnotation(Table.class);
		/*
		 * equals 사용방법을 참고 바랍니다.
		 * http://egloos.zum.com/nestofeagle/v/1501427
		 * 
		 * if (!table.createQuery().equals(""))
		 */
		if (!"".equals(table.createQuery()))
			createQuery = table.createQuery();
		//if (!table.value().equals(""))
		if (!"".equals(table.value()))
			tableName = table.value();
		//if (!table.table_suffix().equals(""))
		if (!table.table_suffix().equals(""))
			table_suffix = table.table_suffix();
	}

	private static final String CREATE_TABLE = "CREATE TABLE `%s` %s %s";

	public void createTable() {
		String sql = createQuery;
		if (sql == null)
			sql = String.format(CREATE_TABLE, tableName, getColumnString(), table_suffix);
		dao.execute(sql);
	}

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS `%s`";

	public void dropTable() {
		String sql = String.format(DROP_TABLE, tableName);
		dao.execute(sql);
	}

	public void reset() {
		dropTable();
		createTable();
	}

	
	private static final String PRIMARY_KEY = "PRIMARY KEY";
	private Map<String, SqlFunction> functions = new HashMap<String, SqlFunction>();

	private String getColumnString() {
		Field[] fields = tableClass.getDeclaredFields();
		String result = "(";

		

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(OtherTable.class))
				continue;
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			SqlFieldNormal fm = (SqlFieldNormal) SqlField.getInstance(fields[i]);
			result += fm.getFieldString() + ", ";
			if (fields[i].isAnnotationPresent(Key.class)) {
				addFunction(fm, PRIMARY_KEY);
			}
			if (fields[i].isAnnotationPresent(Column.class)) {
				String[] fs = fields[i].getAnnotation(Column.class).function();
				for (int j = 0; j < fs.length; j++)
					if (!fs[j].equals(""))
						addFunction(fm, fs[j].toUpperCase());
			}
		}

		for (Map.Entry<String, SqlFunction> entry : functions.entrySet()) {
			result += entry.getValue().getFunctionString(entry.getKey()) + ", ";
		}
		if (!functions.isEmpty())
			result = result.substring(0, result.length() - 2);
		result += ")";
		return result;
	}

	private void addFunction(SqlFieldNormal fm, String key) {
		SqlFunction sf = functions.get(key);
		if (sf == null) {
			sf = new SqlFunction();
			functions.put(key, sf);
		}
		sf.add(fm);
	}

	@Override
	public String toString() {
		return tableName + getColumnString();
	}
}

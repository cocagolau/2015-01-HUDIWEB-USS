package lib.database.maker;

import java.lang.reflect.Field;

import lib.database.DAO;
import lib.database.annotation.Key;
import lib.database.annotation.OtherTable;
import lib.database.annotation.Table;
import lib.database.annotation.Unique;
import lib.database.sql.SqlField;
import lib.database.sql.SqlFieldNormal;
import lib.setting.Setting;

public class TableMaker {

	private DAO dao;
	private Class<?> tableClass;
	private String tableName;
	private String table_suffix;

	public TableMaker(Class<?> tableObj) {
		dao = new DAO();
		tableClass = tableObj;
		tableName = tableClass.getSimpleName();
		table_suffix = Setting.get("database", "default", "table_suffix");
		if (!tableClass.isAnnotationPresent(Table.class))
			return;
		Table table = tableClass.getAnnotation(Table.class);
		if (!table.value().equals(""))
			tableName = table.value();
		if (!table.table_suffix().equals(""))
			table_suffix = table.table_suffix();
	}

	private static final String CREATE_TABLE = "CREATE TABLE `%s` %s %s";

	public void createTable() {
		String sql = String.format(CREATE_TABLE, tableName, getColumnString(), table_suffix);
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
	private static final String UNIQUE = "UNIQUE";

	private String getColumnString() {
		Field[] fields = tableClass.getDeclaredFields();
		String result = "(";

		Function primaryKey = new Function();
		Function unique = new Function();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(OtherTable.class))
				continue;
			SqlFieldNormal fm = (SqlFieldNormal) SqlField.getInstance(fields[i]);
			result += fm.getFieldString() + ", ";
			if (fields[i].isAnnotationPresent(Key.class)) {
				primaryKey.add(fm);
			}
			if (fields[i].isAnnotationPresent(Unique.class)) {
				unique.add(fm);
			}
		}

		result += primaryKey.getFunctionString(PRIMARY_KEY);

		if (unique.hasItem())
			result += ", " + unique.getFunctionString(UNIQUE);

		result += ")";
		return result;
	}

	@Override
	public String toString() {
		return tableName + getColumnString();
	}
}

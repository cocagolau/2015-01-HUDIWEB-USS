package lib.database.sql;

import java.util.HashMap;
import java.util.Map;

import lib.database.annotation.Table;

public class SqlTable {
	private String tableName;

	private static Map<Class<?>, SqlTable> map = new HashMap<Class<?>, SqlTable>();

	public static SqlTable getInstance(Class<?> tableClass) {
		SqlTable result = map.get(tableClass);
		if (result != null)
			return result;
		result = new SqlTable(tableClass);
		map.put(tableClass, result);
		return result;
	}

	private SqlTable(Class<?> tableClass) {
		tableName = tableClass.getSimpleName();
		if (!tableClass.isAnnotationPresent(Table.class))
			return;
		Table table = tableClass.getAnnotation(Table.class);
		if (!table.value().equals(""))
			tableName = table.value();
	}

	public String getTableName() {
		return tableName;
	}

}

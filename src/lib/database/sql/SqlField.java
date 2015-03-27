package lib.database.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lib.database.annotation.OtherTable;

public interface SqlField {

	static Map<Field, SqlField> map = new HashMap<Field, SqlField>();

	public static SqlField getInstance(Field field) {
		SqlField result = map.get(field);
		if (result != null)
			return map.get(field);
		if (field.isAnnotationPresent(OtherTable.class)) {
			result = new SqlFieldOtherTable(field);
		} else {
			result = new SqlFieldNormal(field);
		}
		map.put(field, result);
		return result;
	}

	String getColumnName();

}

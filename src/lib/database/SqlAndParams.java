package lib.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SqlAndParams {
	private static final String SET = " SET ";
	private static final String DETER = "=?, ";
	private static final String GET = "get";
	private String fieldNames;

	public String getFieldNames() {
		return fieldNames;
	}

	public String getInsertString(String tableName) {
		return "INSERT " + tableName + SET + fieldNames;
	}

	public String getUpdateString(String tableName, String suffix) {
		return "UPDATE " + tableName + SET + fieldNames + " " + suffix;
	}

	private List<Object> params;
	private List<Field> fields;

	SqlAndParams(Object record) {
		Class<?> cLass = record.getClass();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<Object>();
		this.fields = new ArrayList<Field>();
		fieldNames = new String();
		for (int i = 0; i < fields.length; i++) {
			try {
				Object param = cLass.getMethod(Parser.upperString(GET, fields[i].getName())).invoke(record);
				if (param == null)
					continue;
				params.add(param);
				this.fields.add(fields[i]);
				fieldNames += fields[i].getName() + DETER;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		if (fieldNames.length() > 0)
			fieldNames = fieldNames.substring(0, fieldNames.length() - 2);
	}

	@Override
	public String toString() {
		return "SqlAndParams [sql=" + fieldNames + ", params=" + params + ", fields=" + fields + "]";
	}
}

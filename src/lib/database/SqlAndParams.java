package lib.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SqlAndParams {
	private static final String SET = " SET ";
	private static final String DETER = "=?, ";
	private static final String GET = "get";

	private List<Object> params;
	private List<Field> fields;

	public String getFieldNames() {
		String result = new String();
		for (int i = 0; i < fields.size(); i++)
			result += fields.get(i).getName() + DETER;
		if (result.length() > 0)
			result = result.substring(0, result.length() - 2);
		return result;
	}

	public String getInsertString(String tableName) {
		return "INSERT " + tableName + SET + getFieldNames();
	}

	public String getUpdateString(String tableName, String suffix) {
		return "UPDATE " + tableName + SET + getFieldNames() + " " + suffix;
	}

	public SqlAndParams(Object record) {
		Class<?> cLass = record.getClass();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<Object>();
		this.fields = new ArrayList<Field>();
		for (int i = 0; i < fields.length; i++) {
			try {
				Object param = cLass.getMethod(Parser.upperString(GET, fields[i].getName())).invoke(record);
				if (param == null)
					continue;
				params.add(param);
				this.fields.add(fields[i]);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String toString() {
		return "SqlAndParams [sql=" + getFieldNames() + ", params=" + params + ", fields=" + fields + "]";
	}
}

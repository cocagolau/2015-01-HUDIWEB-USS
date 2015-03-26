package lib.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lib.database.annotation.Key;

public class SqlParams {
	private static final String DETER = "=?, ";
	private static final String GET = "get";

	private List<Object> params;
	private List<Object> keyParams;
	private List<SqlField> fields;
	private List<SqlField> keyFields;
	private String tableName;

	
	public String getFieldNames() {
		if (fields == null)
			return "";
		String result = new String();
		for (int i = 0; i < fields.size(); i++)
			result += fields.get(i).getColumnName() + DETER;
		if (result.length() > 0)
			result = result.substring(0, result.length() - 2);
		return result;
	}

	public String getKeyFieldNames() {
		if (keyFields == null)
			return "";
		String result = new String();
		for (int i = 0; i < keyFields.size(); i++)
			result += keyFields.get(i).getColumnName() + DETER;
		if (result.length() > 0)
			result = result.substring(0, result.length() - 2);
		return result;
	}

	public String getIntegratedFieldNames() {
		String key = getKeyFieldNames();
		String field = getFieldNames();
		if (key.length() == 0 && field.length() == 0)
			return "";
		if (key.length() == 0)
			return field;
		if (field.length() == 0)
			return key;
		return key + ", " + field;
	}

	public List<Object> getIntegratedParams() {
		List<Object> result = new ArrayList<Object>();
		if (params != null)
			result.addAll(params);
		if (keyParams != null)
			result.addAll(keyParams);
		return result;
	}

	public static boolean hasKey(Object obj) {
		return new SqlParams(obj).getKeyParams().size() != 0;
	}

	public SqlParams(Object record) {
		Class<?> cLass = record.getClass();
		SqlTable table = SqlTable.getInstance(cLass);
		tableName = table.getTableName();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<Object>();
		keyParams = new ArrayList<Object>();
		this.fields = new ArrayList<SqlField>();
		this.keyFields = new ArrayList<SqlField>();
		for (int i = 0; i < fields.length; i++) {
			try {
				Object param = cLass.getMethod(Parser.upperString(GET, fields[i].getName())).invoke(record);
				if (param == null)
					continue;
				if (fields[i].isAnnotationPresent(Key.class)) {
					keyParams.add(param);
					this.keyFields.add(SqlField.getInstance(fields[i]));
					continue;
				}
				params.add(param);
				this.fields.add(SqlField.getInstance(fields[i]));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}

	}

	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "SqlAndParams [sql=" + getFieldNames() + ", params=" + params + ", fields=" + fields + "]";
	}

	public List<Object> getParams() {
		return params;
	}

	public List<Object> getKeyParams() {
		return keyParams;
	}

}

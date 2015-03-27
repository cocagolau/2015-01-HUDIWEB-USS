package lib.database.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.database.Parser;
import lib.database.annotation.Key;

public class KeyParams {
	protected static final String GET = "get";

	protected List<FieldObject> params;
	protected List<FieldObject> keyParams;
	protected String tableName;

	public String getFieldNames(String deter) {
		String result = new String();
		if (!hasParams())
			return result;
		for (int i = 0; i < params.size(); i++)
			result += params.get(i).getColumnName() + deter;
		result = result.substring(0, result.length() - (deter.length() - 2));
		return result;
	}

	public boolean hasParams() {
		return params.size() != 0;
	}
	

	public String getKeyFieldNames(String deter) {
		String result = new String();
		if (!hasKeyParams())
			return result;
		for (int i = 0; i < keyParams.size(); i++)
			result += keyParams.get(i).getColumnName() + deter;
		result = result.substring(0, result.length() -  (deter.length() - 2));
		return result;
	}

	public boolean hasKeyParams() {
		return keyParams.size() != 0;
	}

	public boolean isEmpty() {
		return !hasKeyParams() && !hasParams();
	}

	public String getIntegratedFieldNames(String deter) {
		if (isEmpty())
			return "";
		String field = getFieldNames(deter);
		String key = getKeyFieldNames(deter);
		if (!hasKeyParams())
			return field;
		if (!hasParams())
			return key;
		
		return key + deter + field;
	}

	public List<Object> getIntegratedParams() {
		List<Object> result = new ArrayList<Object>();
		result.addAll(getParams());
		result.addAll(getKeyParams());
		return result;
	}

	public List<Object> getParams() {
		List<Object> result = new ArrayList<Object>();
		params.forEach(param -> {
			result.add(param.getParam());
		});
		return result;
	}

	public List<Object> getKeyParams() {
		List<Object> result = new ArrayList<Object>();
		keyParams.forEach(param -> {
			result.add(param.getParam());
		});
		return result;
	}

	private static Map<Class<?>, KeyParams> map = new HashMap<Class<?>, KeyParams>();

	public static KeyParams getInstance(Class<?> cLass) {
		KeyParams result = map.get(cLass);
		if (result != null)
			return map.get(cLass);
		result = new KeyParams(cLass);
		map.put(cLass, result);
		return result;
	}

	private KeyParams(Class<?> cLass) {
		SqlTable table = SqlTable.getInstance(cLass);
		tableName = table.getTableName();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<FieldObject>();
		keyParams = new ArrayList<FieldObject>();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Key.class)) {
				keyParams.add(new FieldObject(null, fields[i]));
				continue;
			}
			params.add(new FieldObject(null, fields[i]));
		}
	}

	public KeyParams() {
	}

	public KeyParams(Object record) {
		Class<?> cLass = record.getClass();
		SqlTable table = SqlTable.getInstance(cLass);
		tableName = table.getTableName();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<FieldObject>();
		keyParams = new ArrayList<FieldObject>();
		for (int i = 0; i < fields.length; i++) {
			try {
				Object param = cLass.getMethod(Parser.upperString(GET, fields[i].getName())).invoke(record);
				if (param == null)
					continue;
				if (fields[i].isAnnotationPresent(Key.class)) {
					keyParams.add(new FieldObject(param, fields[i]));
					continue;
				}
				params.add(new FieldObject(param, fields[i]));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public String getTableName() {
		return tableName;
	}

}

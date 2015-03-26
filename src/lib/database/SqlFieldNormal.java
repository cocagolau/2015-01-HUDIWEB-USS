package lib.database;

import java.lang.reflect.Field;
import java.util.Date;

import lib.database.annotation.Column;
import lib.database.annotation.Key;
import lib.setting.Setting;

public class SqlFieldNormal implements SqlField {

	SqlFieldNormal(Field field) {
		SqlTable table = SqlTable.getInstance(field.getDeclaringClass());
		this.tableName = table.getTableName();
		this.field = field;
		setCondition();
		setType();
		setFieldString();
	}

	private String tableName;
	private Field field;
	private String columnName;

	private String fieldString;
	private String condition;
	private String type;

	@Override
	public String getColumnName() {
		return columnName;
	}

	public String getWrappedColumnName() {
		return "`" + columnName + "`";
	}

	public String getFieldString() {
		return fieldString;
	}

	private final static String SPACE = " ";
	private static final String BIGINT = "BIGINT";
	private static final String INTEGER = "INTEGER";
	private static final String VARCHAR = "VARCHAR(255)";
	private static final String DATETIME = "DATETIME";
	private static final String FLOAT = "FLOAT";
	private static final String INT_DEFAULT = "NOT NULL DEFAULT 0";
	private static final String DATE_DEFAULT = "NOT NULL DEFAULT CURRENT_TIMESTAMP";
	private static final String STRING_DEFAULT = "NOT NULL DEFAULT ''";

	private void setCondition() {
		Class<?> t = field.getType();
		String result = null;
		if (t.equals(Integer.class) || t.equals(int.class)) {
			String condition = Setting.get("defaultCondition", "Integer");
			result = condition == null ? INT_DEFAULT : condition;
		} else if (t.equals(String.class)) {
			String condition = Setting.get("defaultCondition", "String");
			result = condition == null ? STRING_DEFAULT : condition;
		} else if (t.equals(Date.class)) {
			String condition = Setting.get("defaultCondition", "Date");
			result = condition == null ? DATE_DEFAULT : condition;
		} else if (t.equals(long.class) || t.equals(Long.class)) {
			String condition = Setting.get("defaultCondition", "Long");
			result = condition == null ? INT_DEFAULT : condition;
		} else if (t.equals(float.class) || t.equals(Float.class)) {
			String condition = Setting.get("defaultCondition", "Float");
			result = condition == null ? INT_DEFAULT : condition;
		}
		this.condition = result;
	}

	private void setType() {
		Class<?> t = field.getType();
		String result = null;
		if (t.equals(Integer.class) || t.equals(int.class)) {
			String type = Setting.get("defaultType", "Integer");
			result = type == null ? INTEGER : type;
		} else if (t.equals(String.class)) {
			String type = Setting.get("defaultType", "String");
			result = type == null ? VARCHAR : type;
		} else if (t.equals(Date.class)) {
			String type = Setting.get("defaultType", "Date");
			result = type == null ? DATETIME : type;
		} else if (t.equals(long.class) || t.equals(Long.class)) {
			String type = Setting.get("defaultType", "Long");
			result = type == null ? BIGINT : type;
		} else if (t.equals(float.class) || t.equals(Float.class)) {
			String type = Setting.get("defaultType", "Float");
			result = type == null ? FLOAT : type;
		}
		this.type = result;
	}

	private void setFieldString() {
		String result = new String();
		columnName = tableName + "_" + field.getName();
		if (!field.isAnnotationPresent(Column.class)) {
			result += getWrappedColumnName() + SPACE + type + SPACE;
			if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
				result += "AUTO_INCREMENT" + SPACE + "NOT NULL";
				fieldString = result;
				return;
			}
			result += condition;
			fieldString = result;
			return;
		}
		Column column = field.getAnnotation(Column.class);
		if (!column.value().equals(""))
			columnName = column.value();
		result += getWrappedColumnName() + SPACE;

		if (column.DATA_TYPE().equals(""))
			result += type + SPACE;
		else
			result += column.DATA_TYPE() + SPACE;

		if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
			result += "AUTO_INCREMENT" + SPACE;
			fieldString = result;
			return;
		}

		if (column.NULL())
			result += "NULL" + SPACE;
		else
			result += "NOT NULL" + SPACE;

		if (!column.DEFAULT().equals(""))
			result += "DEFAULT" + SPACE + column.DEFAULT();
		
		fieldString = result;
	}

}

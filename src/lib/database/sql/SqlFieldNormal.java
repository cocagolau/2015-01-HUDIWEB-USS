package lib.database.sql;

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
		setFieldString();
	}

	private String tableName;
	private Field field;
	private String columnName;

	private String fieldString;

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

	private void setCondition() {
		Class<?> t = field.getType();
		if (t.equals(Integer.class) || t.equals(int.class)) {
			setSettings("Integer");
		} else if (t.equals(String.class)) {
			setSettings("String");
		} else if (t.equals(Date.class)) {
			setSettings("Date");
		} else if (t.equals(long.class) || t.equals(Long.class)) {
			setSettings("Float");
		} else if (t.equals(float.class) || t.equals(Float.class)) {
			setSettings("Long");
		}
	}

	private String defaultValue;
	private String nullType;
	private String type;

	private void setSettings(String type) {
		defaultValue = "";
		nullType = "NULL";
		this.type = Setting.get("database", "default", type, "DATATYPE");
		if (!Boolean.parseBoolean(Setting.get("database", "default", type, "NOT NULL")))
			return;
		nullType = "NOT " + nullType;
		if (!Boolean.parseBoolean(Setting.get("database", "default", type, "hasDefault")))
			return;
		String defaultvalue = Setting.get("database", "default", type, "DEFAULT");
		if (type.equals("String") && defaultvalue.equals(""))
			defaultvalue = "''";
		defaultValue += "DEFAULT " + defaultvalue;
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
			result += nullType + SPACE + defaultValue;
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

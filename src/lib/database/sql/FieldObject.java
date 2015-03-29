package lib.database.sql;

import java.lang.reflect.Field;

import lib.database.exception.RegexNotMatches;

public class FieldObject {

	Object param;
	SqlField field;

	public FieldObject(Object param, Field field) throws RegexNotMatches {
		this.param = param;
		this.field = SqlField.getInstance(field);
		RegexCheck.check(param, field);
	}

	public Object getParam() {
		return param;
	}

	public String getColumnName() {
		return field.getColumnName();
	}

}

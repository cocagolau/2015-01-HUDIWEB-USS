package lib.database.sql;

import java.lang.reflect.Field;

import lib.database.exception.RegexNotMatchedException;

public class FieldObject {

	Object param;
	SqlField field;

	public FieldObject(Object param, Field field) {
		this.param = param;
		this.field = SqlField.getInstance(field);
		if(!RegexCheck.check(param, field))
			throw new RegexNotMatchedException();
	}

	public Object getParam() {
		return param;
	}

	public String getColumnName() {
		return field.getColumnName();
	}

}

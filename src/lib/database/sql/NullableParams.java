package lib.database.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import lib.database.Parser;

public class NullableParams extends KeyParams {
	
	public NullableParams(Object record) {
		Class<?> cLass = record.getClass();
		SqlTable table = SqlTable.getInstance(cLass);
		tableName = table.getTableName();
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<FieldObject>();
		keyParams = new ArrayList<FieldObject>();
		for (int i = 0; i < fields.length; i++) {
			try {
				Object param = cLass.getMethod(Parser.upperString(GET, fields[i].getName())).invoke(record);
				if (param == null) {
					params.add(new FieldObject(param, fields[i]));
					continue;
				}
				keyParams.add(new FieldObject(param, fields[i]));
				continue;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
}

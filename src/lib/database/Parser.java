package lib.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class Parser {

	private static final String SET = "set";

	public static String upperString(String prefix, String fieldName) {
		return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static <T> T setObject(Class<T> cLass, Map<String, Object> record) {
		Field[] fields = cLass.getDeclaredFields();
		T result = null;
		try {
			result = cLass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fields.length; i++) {
			Object obj = record.get(cLass.getSimpleName() + "_" + fields[i].getName());
			if (obj == null)
				continue;
			try {
				Method setterMethod = cLass.getMethod(upperString(SET, fields[i].getName()), obj.getClass());
				if (setterMethod == null)
					continue;
				try {
					setterMethod.invoke(result, obj);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				}
			} catch (NoSuchMethodException | SecurityException e) {
			}
		}
		return result;
	}

}

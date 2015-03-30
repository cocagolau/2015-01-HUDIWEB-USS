package lib.database.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lib.database.annotation.RequiredRegex;

public class RegexCheck {

	private static Map<Field, Pattern> patternMap = new HashMap<Field, Pattern>();

	public static boolean check(Object param, Field field) {
		if (!field.isAnnotationPresent(RequiredRegex.class))
			return true;
		Pattern matcher = getMatcher(field);
		if (matcher.matcher(param.toString()).matches()) {
			return true;
		}
		return false;
	}

	private static Pattern getMatcher(Field field) {
		Pattern pattern = patternMap.get(field);
		if (pattern != null)
			return pattern;
		String regex = field.getAnnotation(RequiredRegex.class).value();
		pattern = Pattern.compile(regex);
		patternMap.put(field, pattern);
		return pattern;
	}

}

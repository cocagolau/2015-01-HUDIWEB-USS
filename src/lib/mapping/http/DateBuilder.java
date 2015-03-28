package lib.mapping.http;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lib.mapping.annotation.DateFormat;
import lib.mapping.dispatch.support.DateParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DateBuilder {

	private static Map<Class<?>, DateParser> map = new HashMap<Class<?>, DateParser>();

	public static <T> Gson getGsonBuilder(Class<T> cLass) {
		DateParser parser = getParser(cLass);
		GsonBuilder gb = new GsonBuilder().registerTypeAdapter(Date.class, parser);
		Gson gson = gb.create();
		return gson;
	}

	private static DateParser getParser(Class<?> cLass) {
		DateParser parser = map.get(cLass);
		if (parser != null)
			return parser;

		parser = new DateParser();
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(DateFormat.class)) {
				parser.addFormat(fields[i].getAnnotation(DateFormat.class).value());
			}
		}
		if (cLass.isAnnotationPresent(DateFormat.class)) {
			parser.addFormat(cLass.getAnnotation(DateFormat.class).value());
		}
		map.put(cLass, parser);
		return parser;
	}

}

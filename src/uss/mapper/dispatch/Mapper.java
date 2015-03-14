package uss.mapper.dispatch;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import uss.mapper.annotation.*;
import uss.mapper.dispatch.support.ClassFinder;
import uss.mapper.dispatch.support.Http;

public class Mapper {

	private final static String CONTROLLER_PATH = "uss.controllers";

	private Map<String, MethodHolder> uriMap = new HashMap<String, MethodHolder>();
	private Map<String, MethodHolder> methodsMap = new HashMap<String, MethodHolder>();

	private static Mapper mapper = new Mapper();

	private Mapper() {
		ClassFinder cf = new ClassFinder();
		cf.find(CONTROLLER_PATH).forEach(cLass -> {
			uriSetting(cLass);
		});
	}

	public static void execute(String url, Http http) {
		MethodHolder method = mapper.uriMap.get(url);
		if (method == null) {
			http.sendError(404);
			return;
		}
		String[] before = method.getMethod().getAnnotation(Mapping.class).before();
		String[] after = method.getMethod().getAnnotation(Mapping.class).after();
		for (int i = 0; i < before.length; i++) {
			if (before[i].equals(""))
				continue;
			mapper.methodsMap.get(before[i]).execute(http);
		}
		method.execute(http);
		for (int i = 0; i < after.length; i++) {
			if (after[i].equals(""))
				continue;
			mapper.methodsMap.get(after[i]).execute(http);
		}
	}

	private void uriSetting(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				uriMap.put(mapping.value(), new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(HttpMethod.class)) {
				HttpMethod method = methods[i].getAnnotation(HttpMethod.class);
				methodsMap.put(method.value(), new MethodHolder(methods[i]));
			}
		}
	}

}

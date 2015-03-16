package lib.mapping.dispatch;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lib.database.DAO;
import lib.mapping.annotation.HttpMethod;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.ClassFinder;
import lib.mapping.dispatch.support.Http;
import uss.setting.Setting;

public class Mapper {

	private Map<String, MethodHolder> uriMap = new HashMap<String, MethodHolder>();
	private Map<String, MethodHolder> methodsMap = new HashMap<String, MethodHolder>();

	private static Mapper mapper = new Mapper();

	private Mapper() {
		ClassFinder cf = new ClassFinder();
		cf.find(Setting.CONTROLLER_PATH).forEach(cLass -> {
			uriSetting(cLass);
		});
	}

	public static void execute(String url, Http http) {
		MethodHolder method = mapper.uriMap.get(url);
		if (method == null) {
			http.sendError(404);
			return;
		}
		DAO exe = null;
		if (method.needDBConnector()) {
			exe = new DAO();
		}
		method.executeBefore(http, exe);
		method.execute(http, exe);
		method.executeAfter(http, exe);
		if (exe != null)
			exe.close();
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

	public static MethodHolder getMethod(String string) {
		return mapper.methodsMap.get(string);
	}

}

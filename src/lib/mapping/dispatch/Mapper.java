package lib.mapping.dispatch;

import java.lang.reflect.Method;

import lib.database.DAO;
import lib.mapping.annotation.HttpMethod;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.ClassFinder;
import lib.mapping.dispatch.support.Http;
import lib.mapping.exception.HandleException;
import lib.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mapper {

	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

	private MethodHolderMap uriMap = new MethodHolderMap();
	private MethodHolderMap methodsMap = new MethodHolderMap();

	private static Mapper mapper = new Mapper();

	private Mapper() {
		ClassFinder cf = new ClassFinder();
		cf.find(Setting.get("controllerPath")).forEach(cLass -> {
			uriSetting(cLass);
		});
	}

	public static void execute(String url, Http http) {
		try {
			MethodHolder method = mapper.uriMap.get(url, http);
			if (method == null) {
				http.sendError(404);
				return;
			}
			logger.debug(url + " > " + method.getMethod().getName());
			DAO dao = null;
			if (method.needDAO()) {
				dao = new DAO();
			}
			
			method.executeBefore(http, dao);
			method.execute(http, dao);
			method.executeAfter(http, dao);

			if (dao != null)
				dao.close();
			http.render();
		} catch (HandleException e) {
			e.handle(http);
			http.render();
		}
	}

	private void uriSetting(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				uriMap.put(mapping.method() + ">" + mapping.value(), new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(HttpMethod.class)) {
				HttpMethod method = methods[i].getAnnotation(HttpMethod.class);
				methodsMap.put(method.value(), new MethodHolder(methods[i]));
			}
		}
	}

	public static MethodHolder getMethod(String string, Http http) {
		return mapper.methodsMap.get(string, http);
	}

}

package lib.mapping.dispatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.database.DAO;
import lib.mapping.annotation.HttpMethod;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.ClassFinder;
import lib.mapping.dispatch.support.Http;
import lib.setting.Setting;

public class Mapper {

	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);
	private Map<String, MethodHolder> methodMap = new HashMap<String, MethodHolder>();
	private UriMap uriMap = new UriMap();

	Mapper() {
		ClassFinder cf = new ClassFinder();
		cf.find(Setting.get("controllerPath")).forEach(cLass -> {
			initSetting(cLass);
		});
	}

	public void execute(String url, Http http) {
		List<String> methods = uriMap.get(url, http);
		if (methods == null) {
			http.sendError(404);
			return;
		}
		DAO dao = null;
		if (needDAO(methods))
			dao = new DAO();
		logger.debug(String.format("Uri:%s -> %s", url, methods.toString()));
		for (int i = 0; i < methods.size(); i++) {
			MethodHolder mh = methodMap.get(methods.get(i));
			if (mh == null)
				continue;
			if (!mh.execute(http, dao))
				break;
		}
		if (dao != null)
			dao.close();
		http.render();
	}

	private boolean needDAO(List<String> methods) {
		for (int i = 0; i < methods.size(); i++) {
			MethodHolder mh = methodMap.get(methods.get(i));
			if (mh == null)
				continue;
			if (mh.getMethod().getParameterCount() == 2)
				return true;
		}
		return false;
	}

	private void initSetting(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				List<String> methodsString = new ArrayList<String>();
				String[] before = mapping.before();
				String[] after = mapping.after();
				String key = mapping.method() + ">" + mapping.value();
				addAll(methodsString, before);
				methodsString.add(key);
				addAll(methodsString, after);
				uriMap.put(key, methodsString);
				methodMap.put(key, new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(HttpMethod.class)) {
				HttpMethod method = methods[i].getAnnotation(HttpMethod.class);
				String methodName = method.value();
				if (methodName.equals(""))
					methodName = methods[i].getName();
				methodMap.put(methodName, new MethodHolder(methods[i]));
			}
		}
	}

	private void addAll(List<String> arrayList, String[] stringArray) {
		for (int j = 0; j < stringArray.length; j++) {
			if (stringArray[j].equals(""))
				continue;
			arrayList.add(stringArray[j]);
		}
	}

}

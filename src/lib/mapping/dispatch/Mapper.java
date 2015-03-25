package lib.mapping.dispatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.database.DAO;
import lib.mapping.annotation.After;
import lib.mapping.annotation.Before;
import lib.mapping.annotation.HttpMethod;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.ClassFinder;
import lib.mapping.dispatch.support.Http;
import lib.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mapper {

	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);
	private Map<String, MethodHolder> methodMap = new HashMap<String, MethodHolder>();
	private UriMap uriMap = new UriMap();
	private List<MethodHolder> beforeList = new ArrayList<MethodHolder>();
	private List<MethodHolder> afterList = new ArrayList<MethodHolder>();

	Mapper() {
		ClassFinder cf = new ClassFinder();
		cf.find(Setting.get("controllerPath")).forEach(cLass -> {
			makeMethodMap(cLass);
		});
		cf.find(Setting.get("controllerPath")).forEach(cLass -> {
			makeUriMap(cLass);
		});
	}

	public void execute(String url, Http http) {
		List<MethodHolder> methods = uriMap.get(url, http);
		if (methods == null) {
			http.sendError(404);
			return;
		}
		DAO dao = null;

		List<MethodHolder> todo = new ArrayList<MethodHolder>();
		
		
		todo.addAll(beforeList);
		todo.addAll(methods);
		todo.addAll(afterList);

		logger.debug(String.format("Uri:%s -> %s", url, todo.toString()));
		
		for (int i = 0; i < todo.size(); i++) {
			MethodHolder mh = todo.get(i);
			if (mh == null)
				continue;
			if (mh.needDAO() && dao == null)
				dao = new DAO();
			if (!mh.execute(http, dao))
				break;
		}
		
		if (dao != null)
			dao.close();

		http.render();
	}

	private void makeMethodMap(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				String key = mapping.method() + ">" + mapping.value();
				methodMap.put(key, new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(HttpMethod.class)) {
				HttpMethod method = methods[i].getAnnotation(HttpMethod.class);
				String methodName = method.value();
				if (methodName.equals(""))
					methodName = methods[i].getName();
				methodMap.put(methodName, new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(Before.class)) {
				beforeList.add(new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(After.class)) {
				afterList.add(new MethodHolder(methods[i]));
			}
		}

	}

	private void makeUriMap(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				List<MethodHolder> methodList = new ArrayList<MethodHolder>();
				String[] before = mapping.before();
				String[] after = mapping.after();
				String key = mapping.method() + ">" + mapping.value();
				addAll(methodList, before);
				methodList.add(methodMap.get(key));
				addAll(methodList, after);
				uriMap.put(key, methodList);
			}
		}

	}

	private void addAll(List<MethodHolder> methodList, String[] stringArray) {
		for (int j = 0; j < stringArray.length; j++) {
			if (stringArray[j].equals(""))
				continue;
			methodList.add(methodMap.get(stringArray[j]));
		}
	}

}

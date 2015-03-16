package uss.mapper.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import uss.database.util.DBExecuter;
import uss.mapper.annotation.Mapping;
import uss.mapper.dispatch.support.Http;

public class MethodHolder {

	private Map<String, Object> instanceMap = new HashMap<String, Object>();

	private Object instance;
	private Method method;

	public MethodHolder(Method method) {
		this.method = method;
		String className = method.getDeclaringClass().getName();
		Object instance = instanceMap.get(className);
		if (instance == null) {
			try {
				instance = method.getDeclaringClass().getConstructor().newInstance();
				instanceMap.put(className, instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.instance = instance;
	}

	public void execute(Http http) {
		try {
			this.executeBefore(http);
			if (method.getParameterCount() == 0) {
				method.invoke(instance);
				return;
			}
			method.invoke(instance, http);
			this.executeAfter(http);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return;
	}

	public Method getMethod() {
		return method;
	}

	public void executeBefore(Http http) {
		if(!method.isAnnotationPresent(Mapping.class))
			return;
		String[] before = method.getAnnotation(Mapping.class).before();
		for (int i = 0; i < before.length; i++) {
			if (before[i].equals(""))
				continue;
			Mapper.executeMethod(before[i], http);
		}
	}
	
	public void executeAfter(Http http) {
		if(!method.isAnnotationPresent(Mapping.class))
			return;
		String[] after = method.getAnnotation(Mapping.class).after();
		for (int i = 0; i < after.length; i++) {
			if (after[i].equals(""))
				continue;
			Mapper.executeMethod(after[i], http);
		}
	}

	public void execute(Http http, DBExecuter connector) {
		try {
			if (method.getParameterCount() == 1) {
				method.invoke(instance, connector);
				return;
			}
			method.invoke(instance, http, connector);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return;
	}

	public boolean needDBConnector() {
		return method.getAnnotation(Mapping.class).DB();
	}

	

}

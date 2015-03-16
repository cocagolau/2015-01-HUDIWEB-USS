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

	public Method getMethod() {
		return method;
	}

	public void executeBefore(Http http, DBExecuter exe) {
		if (!method.isAnnotationPresent(Mapping.class))
			return;
		String[] before = method.getAnnotation(Mapping.class).before();
		for (int i = 0; i < before.length; i++) {
			if (before[i].equals(""))
				continue;
			Mapper.getMethod(before[i]).execute(http, exe);
		}
	}

	public void executeAfter(Http http, DBExecuter exe) {
		if (!method.isAnnotationPresent(Mapping.class))
			return;
		String[] after = method.getAnnotation(Mapping.class).after();
		for (int i = 0; i < after.length; i++) {
			if (after[i].equals(""))
				continue;
			Mapper.getMethod(after[i]).execute(http, exe);
		}
	}

	public void execute(Http http, DBExecuter exe) {
		try {
			switch (method.getParameterCount()) {
			case 0:
				method.invoke(instance);
				return;
			case 1:
				if (method.getParameterTypes()[0].equals(Http.class)) {
					method.invoke(instance, http);
					return;
				}
				if (method.getParameterTypes()[0].equals(DBExecuter.class)) {
					method.invoke(instance, exe);
					return;
				}
			case 2:
				method.invoke(instance, http, exe);
				return;
			}
			System.out.println("Errrrrrr");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return;
	}

	public boolean needDBConnector() {
		if(method.getAnnotation(Mapping.class).DB())
			return true;
		String[] before = method.getAnnotation(Mapping.class).before();
		for(int i=0; i<before.length;i++){
			if(Mapper.getMethod(before[i]).getMethod().getAnnotation(Mapping.class).DB())
				return true;
		}
		String[] after = method.getAnnotation(Mapping.class).after();
		for(int i=0; i<after.length;i++){
			if(Mapper.getMethod(after[i]).getMethod().getAnnotation(Mapping.class).DB())
				return true;
		}
		return false;
	}

}

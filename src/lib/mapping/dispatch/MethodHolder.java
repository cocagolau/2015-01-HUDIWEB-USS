package lib.mapping.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lib.mapping.dispatch.support.Http;
import lib.mapping.exception.HandleException;

public class MethodHolder {

	private static Map<String, Object> instanceMap = new HashMap<String, Object>();

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

	public boolean execute(Http http) {
		try {
			if (method.getParameterCount() == 0) {
				method.invoke(instance);
				return true;
			}
			method.invoke(instance, http);
			return true;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			if (!e.getCause().getClass().getSuperclass().equals(HandleException.class)) {
				e.printStackTrace();
				return true;
			}
			HandleException e1 = (HandleException) e.getCause();
			e1.handle(http);
			return false;
		}
	}
}

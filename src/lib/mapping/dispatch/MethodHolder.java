package lib.mapping.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lib.database.DAO;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.Http;

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

	public void executeBefore(Http http, DAO dao) {
		if (!method.isAnnotationPresent(Mapping.class))
			return;
		String[] before = method.getAnnotation(Mapping.class).before();
		for (int i = 0; i < before.length; i++) {
			if (before[i].equals(""))
				continue;
			MethodHolder m = Mapper.getMethod(before[i], http);
			if (m == null)
				return;
			m.execute(http, dao);
		}
	}

	public void executeAfter(Http http, DAO dao) {
		if (!method.isAnnotationPresent(Mapping.class))
			return;
		String[] after = method.getAnnotation(Mapping.class).after();
		for (int i = 0; i < after.length; i++) {
			if (after[i].equals(""))
				continue;
			Mapper.getMethod(after[i], http).execute(http, dao);
		}
	}

	public void execute(Http http, DAO dao) {
		try {
			switch (method.getParameterCount()) {
			case 1:
				method.invoke(instance, http);
				return;
			case 2:
				method.invoke(instance, http, dao);
				return;
			}
			System.out.println("첫번째 파라미터는 Http, DAO를 사용할 경우 두번째 파라미터는 DAO입니다.");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return;
	}

	public boolean needDAO() {
		Class<?>[] paramtypes = method.getParameterTypes();
		for (int i = 0; i < paramtypes.length; i++) {
			if (paramtypes[i].equals(DAO.class))
				return true;
		}
		if (!method.isAnnotationPresent(Mapping.class))
			return false;
		String[] before = method.getAnnotation(Mapping.class).before();
		for (int i = 0; i < before.length; i++) {
			if (before[i].equals(""))
				continue;
			MethodHolder m = Mapper.getMethod(before[i], null);
			if (m == null)
				continue;
			if (m.needDAO())
				return true;
		}
		String[] after = method.getAnnotation(Mapping.class).after();
		for (int i = 0; i < after.length; i++) {
			if (after[i].equals(""))
				continue;
			MethodHolder m = Mapper.getMethod(after[i], null);
			if (m == null)
				continue;
			if (m.needDAO())
				return true;
		}
		return false;
	}
}

package util;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DaoFactory {
	
	private static final Map<Class<?>, Object> map = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> type) {
		Object obj = map.get(type);
		if (obj == null) {
			Reflections reflections = new Reflections("dao.impl");
			Set<Class<? extends T>> set = reflections.getSubTypesOf(type);
			if (set.size() != 1) {
				throw new RuntimeException (
						"There must be one and only one class that implements the interface " +
							type.getName());
			}
			Class<? extends T> class1 = set.iterator().next();
			try {
				obj = class1.getDeclaredConstructor().newInstance();
				map.put(type, obj);
			} catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			
		}
		return (T) obj;
	}

}

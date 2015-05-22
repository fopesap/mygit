package springbook.sug.test;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

public class FieldInjectUtils {
	public static <T> void inject(Object bean, Class<T> clazz, T value) {
		Field field = ReflectionUtils.findField(bean.getClass(), null, clazz);
		if (field == null) throw new IllegalArgumentException("field not found : " + clazz.getName());
		field.setAccessible(true);
		ReflectionUtils.setField(field, bean, value);
	}

	public static <T> void inject(Object bean, Class<T> clazz, String fieldName, T value) {
		Field field = ReflectionUtils.findField(bean.getClass(), fieldName, clazz);	// public static Field findField(Class<?> clazz, String name, Class<?> type)
		if (field == null) throw new IllegalArgumentException("field not found : " + clazz.getName() + " / " + fieldName);
		field.setAccessible(true);
		ReflectionUtils.setField(field, bean, value);
	}
}

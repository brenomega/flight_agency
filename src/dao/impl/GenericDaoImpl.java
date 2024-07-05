package dao.impl;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import dao.GenericDao;
import util.Id;

public class GenericDaoImpl<V> implements GenericDao<V>  {
	
	protected final LinkedHashMap<Integer, V> map;
	private int counter;
	
	public GenericDaoImpl() {
		this.map = new LinkedHashMap<>(16);
		this.counter = 0;
	}
	
	public V include(V obj) {
		Field field = retrieveIdField(obj);
		assignCounterToField(obj, field);
		return map.put(counter, obj);
	}
	
	private void assignCounterToField(V obj, Field field) {
		try {
			field.setAccessible(true);
			field.set(obj, ++counter);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Field retrieveIdField(V obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		throw new RuntimeException("There is already a field annotated with @Id");
	}
	
	public V change(V obj) {
		Field field = retrieveIdField(obj);
		assignCounterToField(obj, field);
		return map.put(counter, obj);
	}
	
	public V remove(Integer id) {
		return map.remove(id);
	}
	
	public V retrieveById(Integer id) {
		return map.get(id);
	}
	
	public List<V> retrieveAll() {
		return map.values().stream().toList();
	}
}

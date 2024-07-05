package dao;

import java.util.List;

public interface GenericDao<V> {
	
	V include(V obj);
	V change(V obj);
	V remove(Integer id);
	V retrieveById(Integer id);
	List<V> retrieveAll();
}

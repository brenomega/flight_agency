package dao;

import java.util.List;
import java.util.Map;

public interface GenericDao<V> {
	
	Map<Integer, V> getMap();
    void setMap(Map<Integer, V> map);
    Integer getCounter();
    void setCounter(Integer counter);
	V include(V obj);
	V change(V obj);
	V remove(Integer id);
	V retrieveById(Integer id);
	List<V> retrieveAll();
}

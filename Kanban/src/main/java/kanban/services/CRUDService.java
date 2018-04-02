package kanban.services;

import java.util.List;

public interface CRUDService<T> {
	
	List<?> listAll();
 
    T saveOrUpdate(T domainObject);

}

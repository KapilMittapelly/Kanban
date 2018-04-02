package kanban.repository;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import kanban.model.User;

@Transactional
public interface UserRepository extends CrudRepository<User, String> {
	
	public User findByUserId(int userId);
	
	public User findByEmail(String email);
	
}
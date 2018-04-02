package kanban.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import kanban.model.UserBoard;

@Transactional
public interface UserBoardsRepository extends CrudRepository<UserBoard, Integer> {
	
	public List<UserBoard> findByUserId(int userId);//userId

}

package kanban.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import kanban.model.Board;

@Transactional
public interface BoardsRepository extends CrudRepository<Board, Integer> {
	
	public Board findByBoardId(int boardId);
	
}

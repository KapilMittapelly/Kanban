package kanban.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import kanban.model.Board;
import kanban.model.Item;

@Transactional
public interface ItemsRepository extends CrudRepository<Item, Integer> {
	
	public List<Item> findByItemId(int itemId);
	
	@Query(value = "SELECT * FROM board_item B WHERE B.BOARD_ID = ? AND B.SEQUENCE_NUM IN (SELECT max(SEQUENCE_NUM) from board_item D WHERE D.BOARD_ID = ? AND B.ITEM_ID = D.ITEM_ID group by D.ITEM_ID)", nativeQuery = true)
	List<Item> findByBoardIdAndMaxSeqNum(int boardId1, int boardId2);

	Item findByBoardAndItemIdAndSequenceNum(Board board, int itemId, int sequenceNum);
	
	Item findTopByBoardOrderByItemIdDesc(Board board);
	
	Item findTopByBoardAndItemIdOrderBySequenceNumDesc(Board board, int itemId);
	
	List<Item> findByBoardAndItemId(Board board, int itemId);
	
	List<Item> findByBoard(Board board);
	
}

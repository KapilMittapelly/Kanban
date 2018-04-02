package kanban.services;

import java.util.List;

import kanban.dto.ItemDto;
import kanban.model.Item;

public interface ItemService extends CRUDService<Item> {
	
	public List<ItemDto> findByItemId(int itemId);
	
	public List<ItemDto> findByBoardId(int boardId);
	
	public List<ItemDto> findByBoardIdAndFilterByUserId(int boardId, int userId, boolean filterFlag);
	
	ItemDto createItem(ItemDto itemDto);
	
	ItemDto updateItem(ItemDto itemDto);

	ItemDto updateItemStatus(ItemDto itemDto);
	
	public void deleteItem(ItemDto itemDto);

	//public ItemDto findTopByBoardIdAndItemIdOrderBySequenceNumDesc(int boardId, int itemId);

}
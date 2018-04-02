package kanban.model;

import java.io.Serializable;

public class ItemKey implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private int boardId;
	private int itemId;
	private int sequenceNum;
	
	public int getItemId() {
		return itemId;
	}
	public int getSequenceNum() {
		return sequenceNum;
	}
	public int getBoardId() {
		return boardId;
	}
	@Override
	public String toString() {
		return "ItemKey [boardId=" + boardId + ", itemId=" + itemId + ", sequenceNum=" + sequenceNum + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boardId;
		result = prime * result + itemId;
		result = prime * result + sequenceNum;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemKey other = (ItemKey) obj;
		if (boardId != other.boardId)
			return false;
		if (itemId != other.itemId)
			return false;
		if (sequenceNum != other.sequenceNum)
			return false;
		return true;
	}
}
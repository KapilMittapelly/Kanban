package kanban.model;

import java.io.Serializable;

public class UserBoardKey  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int userId;
	private Board board;
	
	public int getUserId() {
		return userId;
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public String toString() {
		return "UserBoardKey [userId=" + userId + ", board=" + board + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + userId;
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
		UserBoardKey other = (UserBoardKey) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
}

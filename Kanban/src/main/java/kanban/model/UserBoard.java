package kanban.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(UserBoardKey.class)
@Table(name = "user_board")
public class UserBoard {

	@Id
	@NotNull
	@Column(name = "user_id")
	private int userId;
	
	@NotNull
	@Column(name = "access_level")
	private int accessLevel;
	
	@Id
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "UserBoard [userId=" + userId + ", accessLevel=" + accessLevel + ", board=" + board + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accessLevel;
		result = prime * result + ((board == null) ? 0 : 0);
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
		UserBoard other = (UserBoard) obj;
		if (accessLevel != other.accessLevel)
			return false;
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

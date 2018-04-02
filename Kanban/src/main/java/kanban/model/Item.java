package kanban.model;

import java.sql.Date;
import java.sql.Timestamp;

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
@IdClass(ItemKey.class)
@Table(name = "board_item")
public class Item {
	
	@Id
	@NotNull
	@Column(name = "BOARD_ID")
	private int boardId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "board_id", insertable = false, updatable = false)
	private Board board;
	
	@Id
	@NotNull
	@Column(name = "ITEM_ID")
	private int itemId;
	
	@Id
	@NotNull
	@Column(name = "SEQUENCE_NUM")
	private int sequenceNum;
	
	@NotNull
	@Column(name = "ITEM_SUMMARY")
	private String itemSummary;
	
	@NotNull
	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;
	
	@NotNull
	@Column(name = "STATUS")
	private short status;
	
	@NotNull
	@Column(name = "PRIORITY")
	private short priority;
	
	@NotNull
	@Column(name = "JIRA_NUM")
	private String jiraNum;
	
	@Column(name = "UPDATE_TMS")
	private Timestamp updateTms;
	
	@NotNull
	@Column(name = "UPDATE_USER")
	private int updateUser;
	
	@NotNull
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@NotNull
	@Column(name = "ASSIGNED_USER")
	private int assignedUser;

	public String getItemSummary() {
		return itemSummary;
	}

	public void setItemSummary(String itemSummary) {
		this.itemSummary = itemSummary;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public String getJiraNum() {
		return jiraNum;
	}

	public void setJiraNum(String jiraNum) {
		this.jiraNum = jiraNum;
	}

	public Timestamp getUpdateTms() {
		return updateTms;
	}

	public void setUpdateTms(Timestamp updateTms) {
		this.updateTms = updateTms;
	}

	public int getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}

	public int getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(int assignedUser) {
		this.assignedUser = assignedUser;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	@Override
	public String toString() {
		return "Item [boardId=" + boardId + ", board=" + board + ", itemId=" + itemId + ", sequenceNum=" + sequenceNum
				+ ", itemSummary=" + itemSummary + ", itemDescription=" + itemDescription + ", status=" + status
				+ ", priority=" + priority + ", jiraNum=" + jiraNum + ", updateTms=" + updateTms + ", updateUser="
				+ updateUser + ", dueDate=" + dueDate + ", assignedUser=" + assignedUser + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assignedUser;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + boardId;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((itemDescription == null) ? 0 : itemDescription.hashCode());
		result = prime * result + itemId;
		result = prime * result + ((itemSummary == null) ? 0 : itemSummary.hashCode());
		result = prime * result + ((jiraNum == null) ? 0 : jiraNum.hashCode());
		result = prime * result + priority;
		result = prime * result + sequenceNum;
		result = prime * result + status;
		result = prime * result + ((updateTms == null) ? 0 : updateTms.hashCode());
		result = prime * result + updateUser;
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
		Item other = (Item) obj;
		if (assignedUser != other.assignedUser)
			return false;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (boardId != other.boardId)
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (itemDescription == null) {
			if (other.itemDescription != null)
				return false;
		} else if (!itemDescription.equals(other.itemDescription))
			return false;
		if (itemId != other.itemId)
			return false;
		if (itemSummary == null) {
			if (other.itemSummary != null)
				return false;
		} else if (!itemSummary.equals(other.itemSummary))
			return false;
		if (jiraNum == null) {
			if (other.jiraNum != null)
				return false;
		} else if (!jiraNum.equals(other.jiraNum))
			return false;
		if (priority != other.priority)
			return false;
		if (sequenceNum != other.sequenceNum)
			return false;
		if (status != other.status)
			return false;
		if (updateTms == null) {
			if (other.updateTms != null)
				return false;
		} else if (!updateTms.equals(other.updateTms))
			return false;
		if (updateUser != other.updateUser)
			return false;
		return true;
	}
}

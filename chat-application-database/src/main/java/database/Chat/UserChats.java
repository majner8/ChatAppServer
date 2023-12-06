package database.Chat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity()
public class UserChats {
	public static final String userChatsTableName="";
	
	public static final String userIDcolumnName="";
	public static final String chatIDcolumnName="";
	public static final String userNickNamecolumnName="";
	public static final String chatNamecolumnName="";
	public static final String memberFromcolumnName="";
	public static final String memberUntilcolumnName="";
	public static final String lastSeenMessageIDcolumnName="";
	public static final String joinChatColumnName="";
	
	@Column(name=UserChats.userIDcolumnName)
	private long userID;
	@Column(name=UserChats.chatIDcolumnName)
	private String chatID;
	@Column(name=UserChats.userNickNamecolumnName)
	//name of User
	private String userNickName;
	@Column(name=UserChats.chatNamecolumnName)
	//each user can have own chatName
	private String chatName;
	@Column(name=UserChats.memberFromcolumnName)
	private LocalDateTime memberFrom;
	@Column(name=UserChats.memberUntilcolumnName)
	private LocalDateTime memberUntil=null;
	@Column(name=UserChats.lastSeenMessageIDcolumnName)
	private String lastSeenMessageID;
	
	@ManyToOne
	@Column(name=UserChats.joinChatColumnName)
	@JoinColumn(name="chatID",referencedColumnName="ChatID")
	private ChatEntity chat;
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getChatID() {
		return chatID;
	}
	public void setChatID(String chatID) {
		this.chatID = chatID;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getChatName() {
		return chatName;
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
	public LocalDateTime getMemberFrom() {
		return memberFrom;
	}
	public void setMemberFrom(LocalDateTime memberFrom) {
		this.memberFrom = memberFrom;
	}
	public LocalDateTime getMemberUntil() {
		return memberUntil;
	}
	public void setMemberUntil(LocalDateTime memberUntil) {
		this.memberUntil = memberUntil;
	}
	public String getLastSeenMessageID() {
		return lastSeenMessageID;
	}
	public void setLastSeenMessageID(String lastSeenMessageID) {
		this.lastSeenMessageID = lastSeenMessageID;
	}
	
	
}

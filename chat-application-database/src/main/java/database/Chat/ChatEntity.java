package database.Chat;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity()
public class ChatEntity {
	public static final String chatEntityColumnName="";
	
	public static final String doesIsGroupChatColumnName="";
	public static final String defaultChatNameColumnName="";
	public static final String chatIDColumnName="";
	public static final String createdByUserIDColumnName="";
	public static final String ownerUserIDColumnName="";

	@Column(name=ChatEntity.chatEntityColumnName)
	private boolean doesGroupChat;
	@Column(name=ChatEntity.defaultChatNameColumnName)
	private String defaultChatName;
	@Id
	@Column(name=ChatEntity.chatIDColumnName)
	private String ChatID;
	@Column(name=ChatEntity.createdByUserIDColumnName)
	private long createdByUserID;
	@Column(name=ChatEntity.ownerUserIDColumnName)
	private long ownerUserID;
	@OneToMany(mappedBy="chat")
	private Set<UserChats> chat;
	public boolean isDoesGroupChat() {
		return doesGroupChat;
	}
	public void setDoesGroupChat(boolean doesGroupChat) {
		this.doesGroupChat = doesGroupChat;
	}
	public String getDefaultChatName() {
		return defaultChatName;
	}
	public void setDefaultChatName(String defaultChatName) {
		this.defaultChatName = defaultChatName;
	}
	public String getChatID() {
		return ChatID;
	}
	public void setChatID(String chatID) {
		ChatID = chatID;
	}
	public long getCreatedByUserID() {
		return createdByUserID;
	}
	public void setCreatedByUserID(long createdByUserID) {
		this.createdByUserID = createdByUserID;
	}
	public long getOwnerUserID() {
		return ownerUserID;
	}
	public void setOwnerUserID(long ownerUserID) {
		this.ownerUserID = ownerUserID;
	}
	public Set<UserChats> getChat() {
		return chat;
	}
	public void setChat(Set<UserChats> chat) {
		this.chat = chat;
	}
	
	
}

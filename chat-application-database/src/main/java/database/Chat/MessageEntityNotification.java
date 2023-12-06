package database.Chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import chat_application_common_Part.Chat.MessageTypeOfAction;
@Entity
public class MessageEntityNotification {

	public static final String typeOfActionColumnName="";
	public static final String messageIDColumnName="";
	@Column(name=MessageEntityNotification.typeOfActionColumnName)
	private MessageTypeOfAction typeOfAction;
	@Column(name=MessageEntityNotification.messageIDColumnName)
	private String messageID;
	
	@JoinColumn(name=MessageEntity.messageIDColumnName, referencedColumnName=messageIDColumnName)
	private MessageEntity message;

	public MessageTypeOfAction getTypeOfAction() {
		return typeOfAction;
	}

	public void setTypeOfAction(MessageTypeOfAction typeOfAction) {
		this.typeOfAction = typeOfAction;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public MessageEntity getMessage() {
		return message;
	}

	public void setMessage(MessageEntity message) {
		this.message = message;
	}
	
	
}

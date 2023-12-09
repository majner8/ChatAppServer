package database.Chat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;


@NamedNativeQuery(
		  name = "getLastMessages",
		  query ="With userChat as "
		  		+ "(select *from "+ChatEntity.chatEntityTableName
		  		+ " as chatname"
		  		+ " where "+ ChatEntity.chatIDColumnName +"= :chatID"+")"
		  		+ "join "+MessageEntity.messageEntityTableName
		  		+ " messages"
		  		+ " on"
		  		+ ""
		  		+ "Select* from( "
		  		+ ""
		  				+ ")"
		  				+ ""
		  				+ ""
		  ,resultSetMapping = "SummaryOrders")
@Entity()
public class MessageEntity {
	public static final String messageEntityTableName="";

	public static final String chatIDColumnName="";
	public static final String senderIDColumnName="";
	public static final String messageIDColumnName="";
	public static final String messageColumnName="";
	public static final String receivedTimeColumnName="";
	public static final String wasMessageRemovedColumnName="";
	public static final String orderColumnName="";
	
	public static final String ColumnNamereferenctMessageID="";
	public static final String ColumnNameextendsAction="";
	public static final String JPQLorderName="order"; 
	@Column(name=orderColumnName)
	private long order;
	@Column(name=MessageEntity.chatIDColumnName)
	private String chatID;
	@Column(name=MessageEntity.senderIDColumnName)
	private long senderID;
	//unique by client
	@Column(name=MessageEntity.messageIDColumnName)
	private String messageID;
	@Column(name=MessageEntity.messageColumnName)
	private String message;
	@Column(name=MessageEntity.receivedTimeColumnName)
	private LocalDateTime receivedTime;
	@Column(name=MessageEntity.wasMessageRemovedColumnName)
	private boolean wasMessageRemoved=false;
	@Column(name=MessageEntity.ColumnNamereferenctMessageID)
    private String referenctMessageID;
	@Column(name=MessageEntity.ColumnNamereferenctMessageID)
    private extendsMessageAction extendsAction;
    /**represent extends Message, contain type of action
     * such as type of reaction to message or just reply e.t.c */
    public static enum extendsMessageAction{
    	
    }
    
	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public String getChatID() {
		return chatID;
	}

	public void setChatID(String chatID) {
		this.chatID = chatID;
	}

	public long getSenderID() {
		return senderID;
	}

	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getMessage() {
		
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(LocalDateTime receivedTime) {
		this.receivedTime = receivedTime;
	}

	public boolean isWasMessageRemoved() {
		return wasMessageRemoved;
	}

	public void setWasMessageRemoved(boolean wasMessageRemoved) {
		this.wasMessageRemoved = wasMessageRemoved;
	}

	public String getReferenctMessageID() {
		return referenctMessageID;
	}

	public void setReferenctMessageID(String referenctMessageID) {
		this.referenctMessageID = referenctMessageID;
	}

	public extendsMessageAction getExtendsAction() {
		return extendsAction;
	}

	public void setExtendsAction(extendsMessageAction extendsAction) {
		this.extendsAction = extendsAction;
	}


	
}

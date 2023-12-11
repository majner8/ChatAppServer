package chat_application_chat.WebSocketEndPoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ChatDTO.MessageDTO;
import chat_application_commonPart.Exception.InvalidDataException;
import database.Chat.MessageEntity;
import database.Chat.MessageRepositoryEntity;

public class WebSocketChatService {

	@Autowired
	private MessageRepositoryEntity messageRepo;
	public void SendMessage(MessageDTO message) {
		// TODO Auto-generated method stub
		MessageEntity entity=new MessageEntity(message);
		//can throw duplicate primary key error
		this.messageRepo.save(entity);
	}

	public void sawMessage(long userID,String messageID,String chatID) {
		// TODO Auto-generated method stub
		
	}

	public void ChangeMessage(MessageDTO message) throws InvalidDataException {
		// TODO Auto-generated method stub
		Optional<MessageEntity> entityOP=this.messageRepo.findById(message.getMessageID());
		if(entityOP.isEmpty())throw new InvalidDataException.MessageWithIdWasNotFound();
		MessageEntity previousEntity=entityOP.get();
		MessageEntity newEntity=new MessageEntity(message);
		newEntity.setVersion(previousEntity.getVersion());
		newEntity.setOrder(previousEntity.getOrder());
		//should be thrown Optimistic locking
		this.messageRepo.save(newEntity);
		
		
	}


}

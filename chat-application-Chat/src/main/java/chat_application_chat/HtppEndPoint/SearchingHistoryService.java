package chat_application_chat.HtppEndPoint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ChatDTO.ChatInformationDTO;
import ChatDTO.MessageDTO;
import ChatDTO.UserChatOverViewDTO;
import database.Chat.ChatEntity;
import database.Chat.MessageEntity;
import database.Chat.MessageRepositoryEntity;
import database.Chat.chatEntityRepository;

public class SearchingHistoryService {
	@Autowired
	private MessageRepositoryEntity MessageRepo;
	@Autowired
	private chatEntityRepository chatRepo;
	
	public List<MessageDTO> loadChatHistory(
			 String chatID,
			 long offSetStart,
			 long offSetEnd){
		List<MessageEntity> messages=this.MessageRepo.findByChatId(chatID, offSetStart, offSetEnd);
	
		//MessageEntity have to be change to DTO
		return messages.stream().map((Entity)->{
			MessageDTO mes;
			if(Entity.getNotification()!=null) {
				//is not normal message
				MessageDTO.MessageNotificationDTO not=new MessageDTO.MessageNotificationDTO();
				not.setTypeOfAction(Entity.getNotification().getTypeOfAction());
				mes=(MessageDTO)not;
				return null;
			}
			else {
				mes=new MessageDTO();
			}
			mes.setChatID(Entity.getChatID());
			mes.setMessage(Entity.getMessage());
			mes.setMessageID(Entity.getMessageID());
			mes.setOrder(Entity.getOrder());
			mes.setReceivedTime(Entity.getReceivedTime());
			mes.setSenderID(Entity.getSenderID());
			mes.setWasMessageRemoved(Entity.isWasMessageRemoved());
			return mes;
		}).collect(Collectors.toList());
		
		
	}
	//will be use native queury and SQL mapping
	public UserChatOverViewDTO loadUserChatOverview(
			 long UserID,
			 long offSetStart,
			 long offSetEnd
			) {
		return null;
	}
	public ChatInformationDTO getChatInformation(
			 String chatID) {
		
		Optional<ChatEntity> entityChat=this.chatRepo.findById(chatID);
		if(entityChat.isEmpty()) {
			return null;
		}
		ChatEntity chat=entityChat.get();
		ChatInformationDTO.chatInformationReuqestDTO dto=new ChatInformationDTO.chatInformationReuqestDTO();
		dto.setCreatedBy(chat.getCreatedByUserID());
		dto.set
		return null;
	}
	public MessageDTO getMessage(
			 String chatID,
			 long MessageOrder)
	{return null;}


}

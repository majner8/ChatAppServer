package chat_application_chat.HtppEndPoint;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ChatDTO.ChatInformationDTO;
import ChatDTO.MessageDTO;
import ChatDTO.UserChatOverViewDTO;
import database.Chat.ChatEntity;
import database.Chat.MessageEntity;
import database.Chat.MessageRepositoryEntity;
import database.Chat.chatEntityRepository;

public class ChatHistoryService {

	@Autowired
	private MessageRepositoryEntity messageRepo;
	@Autowired
	private chatEntityRepository chatRepo;
	
	public List<MessageDTO> loadChatHistory(
			 String chatID,
			 long offSetStart,
			 long offSetEnd){
		
		List<MessageEntity> messages=this.messageRepo.findByChatId(chatID, offSetStart, offSetEnd);
		return messages.stream().map((Entity)->{
			MessageDTO mes;			
			mes=new MessageDTO();
			mes.setChatID(Entity.getChatID());
			mes.setMessage(Entity.getMessage());
			mes.setMessageID(Entity.getMessageID());
			mes.setOrder(Entity.getOrder());
			mes.setReceivedTime(Entity.getReceivedTime());
			mes.setSenderID(Entity.getSenderID());
			mes.setWasMessageRemoved(Entity.isWasMessageRemoved());
			mes.setTypeOfAction(Entity.getExtendsAction());
			mes.setReferencMessageID(Entity.getReferenctMessageID());
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
			ChatEntity chatEnt= this.chatRepo.findById(chatID);
			ChatInformationDTO chat=new ChatInformationDTO();
			
			return null;
		}
}

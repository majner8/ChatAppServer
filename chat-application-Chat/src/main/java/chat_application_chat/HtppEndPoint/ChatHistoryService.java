package chat_application_chat.HtppEndPoint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ChatDTO.ChatInformationDTO;
import ChatDTO.ChatInformationDTO.UserChatInformation;
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
		
		public Optional<ChatInformationDTO> getChatInformation(
				 String chatID) {
			
			Optional<ChatEntity> chatEntOp= this.chatRepo.findById(chatID);
			if(chatEntOp.isEmpty()) return Optional.empty();
			ChatEntity chatEnt=chatEntOp.get();
			ChatInformationDTO chat=new ChatInformationDTO();
			chat.setChatID(chatEnt.getChatID());
			chat.setCreatedByUserID(chatEnt.getCreatedByUserID());
			chat.setDefaultChatName(chatEnt.getDefaultChatName());
			
			List<UserChatInformation> userInChat=
					chatEnt.getChat().stream().map((User)->{
						UserChatInformation u=new UserChatInformation();
						u.setChatName(User.getChatName());
						u.setLastSeenMessageID(User.getLastSeenMessageID());
						u.setMemberFrom(User.getMemberFrom());
						u.setMemberUntil(User.getMemberUntil());
						u.setUserID(User.getUserID());
						u.setUserNickName(User.getUserNickName());
						return u;
						
					}).collect(Collectors.toList());
			
			chat.setUser(userInChat);
			return Optional.of(chat);
		}

		public Optional<MessageDTO> getMessage(
				 String chatID,
				 long MessageOrder)
		{
			Optional<MessageEntity>mesEntOp=this.messageRepo.findByChatIDAndOrder(chatID, MessageOrder);
			if(mesEntOp.isEmpty())return Optional.empty();
		return Optional.of(mesEntOp.get().convertEntityToDTO());}
}

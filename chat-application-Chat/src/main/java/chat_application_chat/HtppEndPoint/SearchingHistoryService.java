package chat_application_chat.HtppEndPoint;

import java.util.Set;


import ChatDTO.ChatInformationDTO;
import ChatDTO.MessageDTO;
import ChatDTO.UserChatOverViewDTO;

public class SearchingHistoryService {

	
	public Set<MessageDTO> loadChatHistory(
			 String chatID,
			 long offSetStart,
			 long offSetEnd){
		return null;
	}
	public UserChatOverViewDTO loadUserChatOverview(
			 long UserID,
			 long offSetStart,
			 long offSetEnd
			) {
		return null;
	}
	public ChatInformationDTO getChatInformation(
			 String chatID) {
		return null;
	}
	public MessageDTO getMessage(
			 String chatID,
			 long MessageOrder)
	{return null;}


}

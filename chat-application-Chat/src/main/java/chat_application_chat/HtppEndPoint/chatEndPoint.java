package chat_application_chat.HtppEndPoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ChatDTO.ChatInformationDTO;
import ChatDTO.MessageDTO;
import ChatDTO.UserChatOverViewDTO;
import chat_application_common_Part.EndPoint.htppChatEndPoint;

public class chatEndPoint implements htppChatEndPoint {

	@Autowired
	private SearchingHistoryService service;

	public ResponseEntity<List<MessageDTO>> loadChatHistory(
			 String chatID,
			 long offSetStart,
			 long offSetEnd){
		List<MessageDTO> messages=this.service.loadChatHistory(chatID, offSetStart, offSetEnd);
		
		return null;
	}
	public ResponseEntity<UserChatOverViewDTO> loadUserChatOverview(
			 long UserID,
			 long offSetStart,
			 long offSetEnd
			) {
		return null;
	}
	public ResponseEntity<ChatInformationDTO> getChatInformation(
			 String chatID) {
		
		return null;
	}
	public ResponseEntity<MessageDTO> getMessage(
			 String chatID,
			 long MessageOrder)
	{return null;}
}

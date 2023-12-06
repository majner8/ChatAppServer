package chat_application_chat.HtppEndPoint;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ChatDTO.ChatInformationDTO;
import ChatDTO.MessageDTO;
import ChatDTO.UserChatOverViewDTO;
import chat_application_common_Part.EndPoint.htppChatEndPoint;

public class chatEndPoint implements htppChatEndPoint {

	@Autowired
	private SearchingHistoryService service;

	@GetMapping("/{chatID}/{offSetStart}/{offSetEnd}")
	public ResponseEntity<Set<MessageDTO>> loadChatHistory(
			 String chatID,
			 long offSetStart,
			 long offSetEnd){
		List<MessageDTO> messages=this.service.loadChatHistory(chatID, offSetStart, offSetEnd);
		
		return null;
	}
	@GetMapping("/UserID/{offSetStart}/{offSetEnd}")
	public ResponseEntity<UserChatOverViewDTO> loadUserChatOverview(
			 long UserID,
			 long offSetStart,
			 long offSetEnd
			) {
		return null;
	}
	@GetMapping("/chatID")
	public ResponseEntity<ChatInformationDTO> getChatInformation(
			@PathVariable String chatID) {
		
		return null;
	}
	@GetMapping("/{chatID}/{MessageOrder}")
	public ResponseEntity<MessageDTO> getMessage(
			@PathVariable String chatID,
			@PathVariable long MessageOrder)
	{return null;}
}

package chat_application_chat.HtppEndPoint;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ChatDTO.ChatInformationDTO;
import ChatDTO.MessageDTO;
import ChatDTO.UserChatOverViewDTO;

public class SearchingHistoryControler {

	@Autowired
	private SearchingHistoryService service;
	@GetMapping("/{chatID}/{offSetStart}/{offSetEnd}")
	public ResponseEntity<Set<MessageDTO>> loadChatHistory(
			@PathVariable String chatID,
			@PathVariable long offSetStart,
/*have to be some limit security*/		@PathVariable long offSetEnd){
		Set<MessageDTO> messages=this.service.loadChatHistory(chatID, offSetStart, offSetEnd);
		
		return null;
	}
	@GetMapping("/UserID/{offSetStart}/{offSetEnd}")
	public ResponseEntity<UserChatOverViewDTO> loadUserChatOverview(
			@PathVariable long UserID,
			@PathVariable long offSetStart,
			@PathVariable long offSetEnd
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

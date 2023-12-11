package chat_application_chat.ChatManagement;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.am

import ChatDTO.MessageDTO;

public class ChatMessageService implements ChatMessageServiceInterface{

	private final Map<String,Chat> mapOfActiveChat=Collections.synchronizedMap(new HashMap<String,Chat>());
	@Override
	public void SendMessage(MessageDTO message, String chatID) {
		// TODO Auto-generated method stub
		Chat chat=this.getActiveChat(chatID);
		
		MessageProperties x;
		Message ne;
	}
	
	public Chat getActiveChat(String chatID) {
		Chat chat=this.mapOfActiveChat.get(chatID);
		if(chat==null) {
			//chat have to be loaded
		}
	}
	private class Chat{
		public void PushMessage(Object message,MessageProperties rabitMQProperties) {
			
		}
	}

}

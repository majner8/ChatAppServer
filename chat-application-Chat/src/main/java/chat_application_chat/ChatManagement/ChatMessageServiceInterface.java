package chat_application_chat.ChatManagement;

import ChatDTO.MessageDTO;

public interface ChatMessageServiceInterface {

	public void SendMessage(MessageDTO message,String chatID);
}

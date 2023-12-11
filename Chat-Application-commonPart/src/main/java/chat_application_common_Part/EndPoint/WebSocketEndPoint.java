package chat_application_common_Part.EndPoint;

import ChatDTO.MessageDTO;

public interface WebSocketEndPoint {

	public static interface chatWebSocketEndPoint{
		
		public void SendMessage(MessageDTO message);
		
		public void sawMessage();
		
		public void ChangeMessage(MessageDTO message);
	}
}

package chat_application_common_Part.EndPoint;

public interface WebSocketEndPoint {

	public static interface chatWebSocketEndPoint{
		
		public void SendMessage();
		
		public void sawMessage();
		
		public void ChangeMessage();
	}
}

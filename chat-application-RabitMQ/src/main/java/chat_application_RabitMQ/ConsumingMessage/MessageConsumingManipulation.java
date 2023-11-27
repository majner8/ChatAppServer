package chat_application_RabitMQ.ConsumingMessage;

import org.springframework.web.socket.WebSocketSession;

public interface MessageConsumingManipulation {

	
	public static interface startConsuming{
		/**Metod initialize consuming process
		 * @return false, if queue does not exist consuming could not start, 
		 * and user have to make database synchronizationBefore.
		 * Otherwise metod return true*/
		public boolean StartConsuming(long userID,String deviceID,WebSocketSession session);

	}
	public static interface stopConsuming{
	 	public void StopConsuming(long userID,String deviceID,WebSocketSession session);

	}
	

}

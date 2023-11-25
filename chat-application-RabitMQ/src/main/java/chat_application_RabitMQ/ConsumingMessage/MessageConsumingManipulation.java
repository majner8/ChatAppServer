package chat_application_RabitMQ.ConsumingMessage;

public interface MessageConsumingManipulation {

	public void StartConsuming(long userID,String deviceID);
	
 	public void StopConsuming(long userID,String deviceID);

}

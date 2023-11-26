package chat_application_RabitMQ.ConsumingMessage;

public interface MessageConsumingManipulation {

	
	public static interface startConsuming{
		/**Metod initialize consuming process
		 * @return false, if queue does not exist consuming could not start, 
		 * and user have to make database synchronizationBefore.
		 * Otherwise metod return true*/
		public boolean StartConsuming(long userID,String deviceID);

	}
	public static interface stopConsuming{
	 	public void StopConsuming(long userID,String deviceID);

	}
	

}

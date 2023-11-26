package chat_application_RabitMQ.QueueManagement;

public interface QueueManagementInterface {
	
	public static interface CreatingQueue{
		public boolean CreateQueue(long deviceID,long userID);
	}
	
	public static interface HaveBeenQueueCreatedYet{
		public boolean doesQueueExist(long deviceID,long UserID);
	}
	public static interface removeQueue{
		
	}
}

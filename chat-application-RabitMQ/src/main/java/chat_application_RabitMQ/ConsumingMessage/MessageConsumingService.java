package chat_application_RabitMQ.ConsumingMessage;

import org.springframework.beans.factory.annotation.Autowired;

import chat_application_RabitMQ.QueueManagement.QueueManagementInterface.HaveBeenQueueCreatedYet;

public class MessageConsumingService implements MessageConsumingManipulation.startConsuming,MessageConsumingManipulation.stopConsuming {

	@Autowired
	private HaveBeenQueueCreatedYet queueExist;
	@Override
	public void StopConsuming(long userID, String deviceID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean StartConsuming(long userID, String deviceID) {
		// TODO Auto-generated method stub
		if(!this.queueExist.doesQueueExist(userID, userID)) return false;
		
		return true;
	}

}

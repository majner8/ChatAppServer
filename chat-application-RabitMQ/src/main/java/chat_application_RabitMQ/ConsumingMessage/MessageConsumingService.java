package chat_application_RabitMQ.ConsumingMessage;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;

import chat_application_RabitMQ.RabitMQQueueNameService;
import chat_application_RabitMQ.QueueManagement.QueueManagementInterface.HaveBeenQueueCreatedYet;

public class MessageConsumingService implements MessageConsumingManipulation.startConsuming,MessageConsumingManipulation.stopConsuming {

	 private final SimpleMessageListenerContainer container;
	
	 @Autowired
	 public MessageConsumingService(ConnectionFactory connectionFactory,MessageListener messageListener) {
		 this.container=new SimpleMessageListenerContainer(connectionFactory);
		 this.container.setMessageListener(messageListener);
	 }
	@Autowired
	private HaveBeenQueueCreatedYet queueExist;
	@Override
	public void StopConsuming(long userID, String deviceID) {
		// TODO Auto-generated method stub
		this.container.removeQueueNames(RabitMQQueueNameService.QueueGeneration(userID, deviceID));
	}

	@Override
	public boolean StartConsuming(long userID, String deviceID) {
		// TODO Auto-generated method stub
		if(!this.queueExist.doesQueueExist(userID, userID)) return false;
		
		this.container.addQueueNames(RabitMQQueueNameService.QueueGeneration(userID, deviceID));
		return true;
	}

}

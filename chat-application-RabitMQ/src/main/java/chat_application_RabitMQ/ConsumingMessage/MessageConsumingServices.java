package chat_application_RabitMQ.ConsumingMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import chat_application_RabitMQ.RabitMQQueueNameService;
import chat_application_RabitMQ.QueueManagement.QueueManagementInterface.HaveBeenQueueCreatedYet;

public class MessageConsumingServices implements MessageConsumingManipulation.startConsuming,MessageConsumingManipulation.stopConsuming {

	
	 @Autowired
	 private ConnectionFactory connectionFactory;
	 @Autowired
	 public MessageConsumingService(ConnectionFactory connectionFactory,MessageListener messageListener) {
		 this.container=new SimpleMessageListenerContainer(connectionFactory);
		 this.container.setMessageListener(messageListener);
	 }
	@Autowired
	private HaveBeenQueueCreatedYet queueExist;
	@Override
	public void StopConsuming(long userID, String deviceID,WebSocketSession session) {
		// TODO Auto-generated method stub
		this.container.removeQueueNames(RabitMQQueueNameService.QueueGeneration(userID, deviceID));
	}

	@Override
	public boolean StartConsuming(long userID, String deviceID,WebSocketSession session) {
		// TODO Auto-generated method stub
		if(!this.queueExist.doesQueueExist(userID, userID)) return false;
			SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
			container.setMessageListener(new WebSocketMessageListener(session));
			container.addQueueNames(RabitMQQueueNameService.QueueGeneration(userID, deviceID));
			session.getat
			return true;
	}

	 public class WebSocketMessageListener implements MessageListener{

		 public WebSocketMessageListener(WebSocketSession wsSession) {
			 
		 }
		@Override
		public void onMessage(Message message) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
}

package chat_application_RabitMQ.ConsumingMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import chat_application_RabitMQ.RabitMQQueueService;
import chat_application_RabitMQ.QueueManagement.QueueManagementInterface.HaveBeenQueueCreatedYet;
import chat_application_RabitMQ.WebSocket.CustomWebSocketHandlerDecorator;

public class MessageConsumingService implements MessageConsumingManipulation.startConsuming,MessageConsumingManipulation.stopConsuming {

	
	
	@Autowired
	private HaveBeenQueueCreatedYet queueExist;
	@Override
	public void StopConsuming(long userID, String deviceID,WebSocketSession session) {
		// TODO Auto-generated method stub
    	SimpleMessageListenerContainer container=(SimpleMessageListenerContainer)session.getAttributes().get(CustomWebSocketHandlerDecorator.sessionRabitMQListenerName);
    	container.stop();
    	
	}

	@Override
	public boolean StartConsuming(long userID, String deviceID,WebSocketSession session) {
		// TODO Auto-generated method stub
		if(!this.queueExist.doesQueueExist(userID, userID)) return false;
    	SimpleMessageListenerContainer container=(SimpleMessageListenerContainer)session.getAttributes().get(CustomWebSocketHandlerDecorator.sessionRabitMQListenerName);
    	container.addQueueNames(RabitMQQueueService.QueueGeneration(userID, deviceID));
		container.start();
		return true;
	}


}


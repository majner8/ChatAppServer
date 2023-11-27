package chat_application_RabitMQ.ConsumingMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component(value="prototype")
public class RabitMQMessageListener implements MessageListener{

	private WebSocketSession wsSession;
	
	public void setWebSocketSession(WebSocketSession wsSession) {
		this.wsSession=wsSession;
	}
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
	}
	 
}

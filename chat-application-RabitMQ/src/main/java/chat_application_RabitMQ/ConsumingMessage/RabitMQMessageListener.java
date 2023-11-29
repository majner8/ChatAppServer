package chat_application_RabitMQ.ConsumingMessage;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import chat_application_commonPart.Logger.Log4j2;

@Component(value="prototype")
public class RabitMQMessageListener implements MessageListener{

	private WebSocketSession wsSession;
	
	public void setWebSocketSession(WebSocketSession wsSession) {
		this.wsSession=wsSession;
	}
	@Override
	public void onMessage(Message message) {
		TextMessage wsMessage=new TextMessage(message.getBody());
		try {
			this.wsSession.sendMessage(wsMessage);
			
		} catch (IOException e) {
			Log4j2.log.debug(Log4j2.MarkerLog.WebSocket.getMarker(),"WS Connection id: "+this.wsSession.getId()+"  "+e);

			
			try {
				this.wsSession.close(CloseStatus.SESSION_NOT_RELIABLE);
				Log4j2.log.debug(Log4j2.MarkerLog.WebSocket.getMarker(),"WS Connection id:"+this.wsSession.getId()+"  was closed");

			} catch (IOException e1) {
				Log4j2.log.debug(Log4j2.MarkerLog.WebSocket.getMarker(),"WS Connection id:"+this.wsSession.getId()+"  "+e1);
				Log4j2.log.debug(Log4j2.MarkerLog.WebSocket.getMarker(),"Cannot closed WS Connection id: "+this.wsSession.getId());

			}
		}
	}
	 
}

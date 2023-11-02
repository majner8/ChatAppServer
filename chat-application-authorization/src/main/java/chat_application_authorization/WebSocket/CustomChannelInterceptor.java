package chat_application_authorization.WebSocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import chat_application_commonPart.Authorization.CustomUserDetails;
import chat_application_commonPart.Logger.Log4j2;

public class CustomChannelInterceptor implements ChannelInterceptor {

	
	 @Override
	    public Message<?> preSend(Message<?> message, MessageChannel channel) {
	        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
	        if (SimpMessageType.CONNECT.equals(accessor.getMessageType())) {
	        	Log4j2.log.debug(Log4j2.LogMarker.WebSocket.getMarker(),"I am saving userSessin to principal");
	        	if(accessor.getUser() instanceof CustomUserDetails) {
	        		CustomUserDetails prin=(CustomUserDetails)accessor.getUser();
		 			   prin.setWebSocketId(accessor.getSessionId());
		 			   Log4j2.log.info(Log4j2.LogMarker.WebSocket.getMarker(),"WebSocket connection was establish. Id was saved in principal");
	        	}
	        	else {
	        		   Log4j2.log.warn(Log4j2.LogMarker.WebSocket.getMarker(),""
	   	 			   		+ "Principal associated with WebSocketHandshake metod, is not instance of CustomUserDetails"
	   	 			   		+ "Socket session id is not store in principal");
	        	}
	        }
	        return message;
	    }
}

package authorization.WebSocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.server.HandshakeInterceptor;

import chat_application_common_Part.Security.CustomSecurityContextHolder;
import chat_application_common_Part.Security.CustomUserDetails;
import database.User.ActivityUserEntity;

public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator implements HandshakeInterceptor {

    public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		ActivityUserEntity activity=new ActivityUserEntity();
		activity.setDeviceID(CustomSecurityContextHolder.getCustomSecurityContext().getDeviceID());		
		activity.setUserID(CustomSecurityContextHolder.getCustomSecurityContext().getUserID());
		activity.setLogin();
		//change principal of CustomUserDetails, with principal with loginActivity
		
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}
	

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	// Logic after connection is established
    	
    	//mark user as active is not necessary, is done during handshake
    	//start consuming from rabitMQ
    	
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // Logic before connection is closed
        super.afterConnectionClosed(session, closeStatus);
    }

}

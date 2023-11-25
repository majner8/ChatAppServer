package chat.application.WebSocket.HandShake;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.server.HandshakeInterceptor;

import chat_application_common_Part.Security.CustomSecurityContextHolder;
import chat_application_common_Part.Security.CustomUserDetails;
import database.User.ActivityUserEntity;
import database.User.ActivityUserEntityRepository;



public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator implements HandshakeInterceptor {

	@Autowired
	private ActivityUserEntityRepository activityRepo;
	
	private final ThreadLocal<ActivityUserEntity> userActivity=new ThreadLocal<ActivityUserEntity>();
	public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		CustomUserDetails CustomUser=CustomSecurityContextHolder.getCustomSecurityContext().getCustomUserDetails();
		ActivityUserEntity activity=new ActivityUserEntity();
		activity.setDeviceID(CustomSecurityContextHolder.getCustomSecurityContext().getDeviceID());		
		activity.setUserID(CustomSecurityContextHolder.getCustomSecurityContext().getUserID());
		activity.setLogin();
		//save activity, if handshake would not be successful, mark them as  unActivite in next method afterHandshake
		this.activityRepo.save(activity);
		this.userActivity.set(activity);
			
		//change principal of CustomUserDetails, with principal with loginActivity
		CustomUserDetails.WebSocketCustomUserDetails user=new CustomUserDetails.WebSocketCustomUserDetails(CustomUser,activity.getPrimaryKey());
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
		CustomSecurityContextHolder.getCustomSecurityContext().setAuthentication(auth);
		
		
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		
		// TODO Auto-generated method stub
		if(exception!=null) {
			//operation was not successful
			ActivityUserEntity activity=this.userActivity.get();
			activity.setLogout(activity.getLogin());
			this.activityRepo.save(activity);
		}
		this.userActivity.remove();
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
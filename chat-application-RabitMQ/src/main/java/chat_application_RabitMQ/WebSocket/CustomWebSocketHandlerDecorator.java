package chat_application_RabitMQ.WebSocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.server.HandshakeInterceptor;

import chat_application_RabitMQ.RabitMQQueueNameService;
import chat_application_common_Part.RabitMQ.ActiveUserWSConnection;
import chat_application_common_Part.Security.CustomSecurityContextHolder;
import chat_application_common_Part.Security.CustomSecurityContextHolder.CustomSecurityContext;
import chat_application_common_Part.Security.CustomUserDetails;
import database.User.ActivityUserEntity;
import database.User.ActivityUserEntityRepository;



public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator implements HandshakeInterceptor {

	@Autowired
	private ActivityUserEntityRepository activityRepo;
	@Autowired
	private ActiveUserWSConnection<String,String> activeUser;
	private final ThreadLocal<ActivityUserEntity> userActivity=new ThreadLocal<ActivityUserEntity>();

	public static final String CopiedSecurityConfigAttribute="";
	
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
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
			WebSocketHandler wsHandler,
			Exception exception) {
		
		// TODO Auto-generated method stub
		if(exception!=null) {
			//operation was not successful
			ActivityUserEntity activity=this.userActivity.get();
			activity.setLogout(activity.getLogin());
			this.activityRepo.save(activity);
		}
		else {
		
		}
		this.userActivity.remove();
	}
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	// Logic after connection is established
    	CustomSecurityContext securityContext = (CustomSecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");

    	String deviceID=securityContext.getDeviceID();
    	long userID=securityContext.getUserID();
    	this.activeUser.put(RabitMQQueueNameService.QueueGeneration(userID, deviceID), session.getId());
    	//mark user as active is not necessary, is done during handshake
    	//cannot start consuming, because sometimes, user have to make synchronization
    	
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	//remove active connection from map
    	CustomSecurityContext securityContext = (CustomSecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");

    	String deviceID=securityContext.getDeviceID();
    	long userID=securityContext.getUserID();
    	this.activeUser.remove(RabitMQQueueNameService.QueueGeneration(userID, deviceID), session.getId());

    	// Logic before connection is closed
        super.afterConnectionClosed(session, closeStatus);
    }

}

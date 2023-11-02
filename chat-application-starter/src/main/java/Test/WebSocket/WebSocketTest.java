package Test.WebSocket;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class WebSocketTest {

	private WebSocketClient webSocketClient = new StandardWebSocketClient();

	public void WebSocketConnectionEstabilishTest(String authenticatedJwtToken) {
		
		try {
			WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
			WebSocketSession session = webSocketClient.doHandshake(
				    new TextWebSocketHandler() {}, headers, new URI("ws://localhost:8080/my-websocket-endpoint")
				).get();
			assertTrue(session.isOpen());
			
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

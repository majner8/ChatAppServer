package chat_application_common_Part.RabitMQ;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;


public class ActiveUserWSConnection <K,V>extends HashMap<K,V>{

	public ActiveUserWSConnection() {
	}

	  @Bean
	    public ActiveUserWSConnection<String, String> activeUserWSConnection() {
	      return (ActiveUserWSConnection)Collections.synchronizedMap(new ActiveUserWSConnection<>());
	    }
}


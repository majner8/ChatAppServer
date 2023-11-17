package chat_application_commonPart.Config;

import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class Durations {

	private final Duration deviceToken;
	
	private final Duration userAuthorizationToken;

	public Duration getDeviceToken() {
		return deviceToken;
	}

	public Duration getUserAuthorizationToken() {
		return userAuthorizationToken;
	}
	
	
}

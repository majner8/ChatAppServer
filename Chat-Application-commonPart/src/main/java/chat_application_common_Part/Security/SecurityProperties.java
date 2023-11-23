package chat_application_common_Part.Security;

import java.time.Duration;
import java.util.Calendar;

import com.auth0.jwt.algorithms.Algorithm;

public interface SecurityProperties {

	public Algorithm getjwtTokenDeviceIDAlgorithm();
	
	public Algorithm getjwtTokenAuthorizationUserAlgorithm();

	public String getTokenDeviceIdPreflix();
	public String getTokenAuthorizationUserPreflix();
	public String getTokenDeviceIdHeaderName();
	public String getTokenAuthorizationUserHederName();
	
	public Calendar getJwtTokenDeviceIdDuration();
	public Calendar getJwtTokenAuthorizationUserDuration();
	

	
}

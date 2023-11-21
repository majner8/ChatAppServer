package authorization.Security.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import authorization.Security.jwtToken;

@Service
public class FilterService {

	public boolean skipDeviceIdFilter(String callEndPoint) {
		
	}
	public boolean skipUserAuthorizationFilter(String callEndPoint) {
		
	}
	
	@Component
	public static abstract class Filter extends OncePerRequestFilter {
		
		@Autowired
		protected FilterService filterService;
		@Autowired
		protected jwtToken.jwtTokenValidationInterface tokenValidator;
	}
}

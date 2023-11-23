package authorization.Security.Filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import authorization.Security.jwtToken;
import chat_application_common_Part.Security.Config.AuthorizationPath;

@Service
public class FilterService {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

	
	private final static String deviceIdPathPatern=AuthorizationPath.deviceIDPath+"/**";
	public boolean skipDeviceIdFilter(String callEndPoint) {
		return pathMatcher.match(this.deviceIdPathPatern, callEndPoint);
	}
	private final static String userAuthorizationPath=AuthorizationPath.AuthorizationPreflix+AuthorizationPath.UnAuthenticatedPreflix+"/**";
	public boolean skipUserAuthorizationFilter(String callEndPoint) {
		return pathMatcher.match(this.userAuthorizationPath, callEndPoint);

	}
	
	@Component
	public static abstract class Filter extends OncePerRequestFilter {
		
		@Autowired
		protected FilterService filterService;
		@Autowired
		protected jwtToken.jwtTokenValidationInterface tokenValidator;
	}
}

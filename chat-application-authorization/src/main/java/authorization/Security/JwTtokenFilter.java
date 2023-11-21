package authorization.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import chat_application_commonPart.httpEndPointPath.AuthorizationPath;

public class JwTtokenFilter  extends OncePerRequestFilter  {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//just verify if calling path is not getdevice id
		//other
		if(request.getRequestURI().equals(AuthorizationPath.authorizationPreflix+AuthorizationPath.loginPath)
				||request.getRequestURI().equals(AuthorizationPath.authorizationPreflix+AuthorizationPath.registerPath)	) {
					filterChain.doFilter(request, response);
					return;
				}
	}

	 

}

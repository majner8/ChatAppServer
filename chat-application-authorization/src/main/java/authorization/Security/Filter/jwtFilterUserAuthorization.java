package authorization.Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;

import authorization.Security.Filter.FilterService.Filter;

public class jwtFilterUserAuthorization extends Filter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(super.filterService.skipUserAuthorizationFilter(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		DecodedJWT token=super.tokenValidator.jwtTokenAuthorizationUserTokenValidator(request);
	
	}

}

package authorization.Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.auth0.jwt.interfaces.DecodedJWT;

import authorization.Security.jwtToken;
import authorization.Security.Filter.FilterService.Filter;
import chat_application_common_Part.Security.CustomSecurityContextHolder;
import chat_application_common_Part.Security.CustomUserDetails;

public class jwtFilterUserAuthorization extends Filter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomSecurityContextHolder.getCustomSecurityContext().setDeviceID((String)request.getAttribute("userID"));
		if(super.filterService.skipUserAuthorizationFilter(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		DecodedJWT token=super.tokenValidator.jwtTokenAuthorizationUserTokenValidator(request);
		CustomUserDetails user=new CustomUserDetails(Long.valueOf(token.getSubject()),token.getClaim(jwtToken.authoriClainName).asLong());
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
		CustomSecurityContextHolder.getCustomSecurityContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
		return;
	}

}

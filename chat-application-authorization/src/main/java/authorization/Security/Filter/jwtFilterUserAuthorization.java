package authorization.Security.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import authorization.Security.jwtToken;
import authorization.Security.Filter.FilterService.Filter;
import chat_application_common_Part.Security.CustomSecurityContextHolder;
import chat_application_common_Part.Security.CustomUserDetails;
import chat_application_common_Part.Security.FilterManagement;
import chat_application_common_Part.Security.SecurityProperties;
@Component
public class jwtFilterUserAuthorization extends Filter implements FilterManagement.authorizationUserFilter{

	@Autowired
	private SecurityProperties securityConfig;
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
		List<SimpleGrantedAuthority> authority=new ArrayList<SimpleGrantedAuthority>();
		authority.add(new SimpleGrantedAuthority(this.securityConfig.getDeviceIDAuthority()));
		CustomUserDetails user=new CustomUserDetails(Long.valueOf(token.getSubject()),token.getClaim(jwtToken.authoriClainName).asLong()
				,authority);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
		CustomSecurityContextHolder.getCustomSecurityContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
		return;
	}

	@Override
	public OncePerRequestFilter getFilter() {
		// TODO Auto-generated method stub
		return this;
	}

}

package authorization.Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

import authorization.Security.Filter.FilterService.Filter;
@Component
public class jwtFilterDeviceId extends Filter {

	@Autowired
	private RequestScopeAuthorizationValue scopeValue;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(super.filterService.skipDeviceIdFilter(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		DecodedJWT token=super.tokenValidator.jwtTokenDeviceIDTokenValidator(request);
		this.scopeValue.setDeviceID(token.getSubject());
		filterChain.doFilter(request, response);
		return;
	}

}

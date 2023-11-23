package authorization.Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import authorization.Security.Filter.FilterService.Filter;
import chat_application_common_Part.Security.CustomSecurityContextHolder;
import chat_application_common_Part.Security.FilterManagement;
@Component
public class jwtFilterDeviceId extends Filter implements FilterManagement.deviceIdFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(super.filterService.skipDeviceIdFilter(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		DecodedJWT token=super.tokenValidator.jwtTokenDeviceIDTokenValidator(request);
		//add userID as attribute
		CustomSecurityContextHolder.getCustomSecurityContext().setDeviceID(token.getSubject());
		filterChain.doFilter(request, response);
		return;
	}

	@Override
	public OncePerRequestFilter getFilter() {
		// TODO Auto-generated method stub
		return this;
	}

}

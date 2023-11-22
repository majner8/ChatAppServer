package authorization.Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authorization.Security.Filter.FilterService.Filter;

public class jwtFilterDeviceId extends Filter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!super.filterService.skipDeviceIdFilter(request.getRequestURI())) {
			super.tokenValidator.deviceIdjwtFilterValidator(request);

		}
		filterChain.doFilter(request, response);
		return;
	}

}

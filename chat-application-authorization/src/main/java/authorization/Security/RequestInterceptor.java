package authorization.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
//for saving login information
public class RequestInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(
	  HttpServletRequest request,
	  HttpServletResponse response, 
	  Object handler) throws Exception{
		
		return true;
	}
	@Override
	public void afterCompletion(
	  HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
	  throws Exception{
		
		return;
	}
	
}

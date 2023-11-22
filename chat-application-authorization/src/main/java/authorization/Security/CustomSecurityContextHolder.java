package authorization.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

public class CustomSecurityContextHolder implements SecurityContextHolderStrategy {

	private final ThreadLocal<SecurityContext> threadLocal=new ThreadLocal<SecurityContext>();
	@Override
	public void clearContext() {
		// TODO Auto-generated method stub
		this.threadLocal.remove();
	}

	@Override
	public SecurityContext getContext() {
		// TODO Auto-generated method stub
		return this.threadLocal.get();
	}

	@Override
	public void setContext(SecurityContext context) {
		// TODO Auto-generated method stub
		this.threadLocal.set(context);
	}

	@Override
	public SecurityContext createEmptyContext() {
		// TODO Auto-generated method stub
		this.setContext(new CustomSecurityContext());
		return this.threadLocal.get();
	}

	public static class CustomSecurityContext implements SecurityContext{
		private Authentication aut;
		//other deviceId information
		@Override
		public Authentication getAuthentication() {
			// TODO Auto-generated method stub
			return this.aut;
		}

		@Override
		public void setAuthentication(Authentication authentication) {
			// TODO Auto-generated method stub
			this.aut=authentication;
		}
		
	}
	
}

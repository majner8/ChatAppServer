package chat_application_common_Part.Security;

import org.springframework.web.filter.OncePerRequestFilter;

public interface FilterManagement {

	public static interface deviceIdFilter{
		public OncePerRequestFilter getFilter();
	}
	public static interface authorizationUserFilter{
		public OncePerRequestFilter getFilter();
	}
}

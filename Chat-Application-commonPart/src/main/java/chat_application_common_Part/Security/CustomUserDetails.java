package chat_application_common_Part.Security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private final long userID;
	private final long databaseVersion;
	private final Collection<? extends GrantedAuthority> authority;
	
	public CustomUserDetails(long userID,long databaseVersion,
			Collection<? extends GrantedAuthority> authority) {
		this.authority=authority;
		this.userID=userID;
		this.databaseVersion=databaseVersion;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authority;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public long getUserID() {
		return this.userID;
	}
	public long getDatabaseVersion() {
		return this.databaseVersion;
	}

}

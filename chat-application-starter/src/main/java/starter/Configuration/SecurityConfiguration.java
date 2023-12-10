package starter.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import chat_application_common_Part.Security.FilterManagement;
import chat_application_common_Part.Security.Config.AuthorizationPath;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private FilterManagement.authorizationUserFilter userAuthorizationFilter;
	@Autowired
	private FilterManagement.deviceIdFilter deviceIDFilter;
	
	
	@Override
	 protected void configure(HttpSecurity http)throws Exception {
		 
		 
		 http.csrf().disable()
		 .formLogin().disable()
		 .logout().disable()	
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and() 
		 .addFilterAfter(this.deviceIDFilter.getFilter(), UsernamePasswordAuthenticationFilter.class)
		 .addFilterAfter(this.userAuthorizationFilter.getFilter(), this.deviceIDFilter.getFilter().getClass())
		 .authorizeRequests()
		 .antMatchers(AuthorizationPath.deviceIDPath).permitAll()
		// .anyRequest().hasAuthority(RoleManagement.deviceIDRole)
		 .antMatchers(AuthorizationPath.AuthorizationPreflix+AuthorizationPath.UnAuthenticatedPreflix+"/**").permitAll()
		 .anyRequest().fullyAuthenticated()
		 ;
	 }


}


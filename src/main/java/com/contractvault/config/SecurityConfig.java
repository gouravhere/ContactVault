package com.contractvault.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.contractvault.services.impl.SecurityCustomUserDetailsService;

@Configuration
public class SecurityConfig {

	// user create and login using java code within memory service
//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails user1 = User
//		.withDefaultPasswordEncoder()
//		.username("admin123")
//		.password("admin123")
//		.roles("ADMIN", "USER")
//		.build();
//
//		UserDetails user2 = User
//		.withDefaultPasswordEncoder()
//		.username("user123")
//		.password("password")
//		.build();
//		
//		var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
//		return inMemoryUserDetailsManager;
//	}
	
	
	@Autowired
	private SecurityCustomUserDetailsService userDetailsService;
	
	@Autowired
	private OAuthAuthenticationSuccessHandler handler;
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	
	//object of user details service
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
	//object of password encode
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
	//configuration
		httpSecurity.authorizeHttpRequests(authorize->{
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});
		
		//any changes related to form login is done below
		//httpSecurity.formLogin(Customizer.withDefaults());
		//using our own login page
		httpSecurity.formLogin(formLogin->{
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/profile");
			//formLogin.failureForwardUrl("/login?error=true");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
//			formLogin.failureHandler(new AuthenticationFailureHandler() {
//				
//				@Override
//				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//						AuthenticationException exception) throws IOException, ServletException {
//					// TODO Auto-generated method stub
//					throw new UnsupportedOperationException(null)
//				}
//			});
			
		});
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		httpSecurity.logout(logoutForm->{
			logoutForm.logoutUrl("/do-logout");
			logoutForm.logoutSuccessUrl("/login?logout=true");
		});
		
		httpSecurity.oauth2Login(oauth->{
			oauth.loginPage("/login");
			oauth.successHandler(handler);
		});
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		
		return new BCryptPasswordEncoder();
		}
	
}

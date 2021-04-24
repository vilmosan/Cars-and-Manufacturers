package hu.pte.ttk.vaadin.vaadindemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	private static final String LOGIN_SUCCESS_URL = "/";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login page
		http.csrf().disable().exceptionHandling().accessDeniedPage("/403")
				.and()

				// Restrict access to our application.
				.authorizeRequests()

				// Allow all flow internal requests.
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
				.antMatchers("/author").hasRole("ADMIN")
				.antMatchers("/user").hasRole("ADMIN")

				// Allow all requests by logged in users.
				.anyRequest().authenticated()

				// Configure the login page.
				.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL).successForwardUrl(LOGIN_SUCCESS_URL)
				.failureUrl(LOGIN_FAILURE_URL)

				// Configure logout
				.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}

	/**
	 * Allows access to static resources, bypassing Spring security.
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
				// Vaadin Flow static resources
				"/VAADIN/**",

				// the standard favicon URI
				"/favicon.ico",

				// the robots exclusion standard
				"/robots.txt",

				// web application manifest
				"/manifest.webmanifest",
				"/sw.js",
				"/offline-page.html",

				// icons and images
				"/icons/**",
				"/images/**",

				// (development mode) static resources
				"/frontend/**",

				// (development mode) webjars
				"/webjars/**",

				// (development mode) H2 debugging console
				"/h2-console/**",

				// (production mode) static resources
				"/frontend-es5/**", "/frontend-es6/**");
	}
}
/* @Bean
@Override
public UserDetailsService userDetailsService() {
    UserDetails user =
            User.withUsername("user")
                    .password(new BCryptPasswordEncoder().encode("{noop}password"))
                    .roles("USER")
                    .build();
    UserDetails admin =
            User.withUsername("admin")
                    .password(new BCryptPasswordEncoder().encode("{noop}password"))
                    .roles("ADMIN")
                    .build();
    return new InMemoryUserDetailsManager(user, admin);
}*/

package org.nhnnext.config;

import javax.annotation.Resource;

import org.nhnnext.service.CustomResourceServerTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Resource(name="customUserDetailsService")
	private UserDetailsService customUserDetailsService;
	
	@Autowired 
	private ResourceServerProperties sso;
	
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext; 
	
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
        http
        .authorizeRequests()
        	.antMatchers(HttpMethod.GET, "/b/**").authenticated()
        	.antMatchers("/boards/**").access("hasRole('ROLE_USER')")
            .anyRequest().permitAll()
            .and()
        .formLogin()
            .loginPage("/users/loginForm")
            .loginProcessingUrl("/users/login")
            .permitAll()
            .and()
        .logout()
            .permitAll();
    
        http.httpBasic();

//		http
//		.authorizeRequests()
//    	   .antMatchers(HttpMethod.GET, "/b/**").authenticated()
//    	   .antMatchers("/boards/**").access("hasRole('ROLE_USER')")
//           .anyRequest().permitAll()
//           .and()
//		.formLogin()
//          .loginPage("/users/login")
//          .loginProcessingUrl("/users/login")
//          .permitAll()
//		.and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
//		.and().logout().logoutSuccessUrl("/").permitAll()
//		.and().csrf().csrfTokenRepository(csrfTokenRepository())
//		.and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)		
//		.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);

//		http
//        .authorizeRequests()
//        	.antMatchers(HttpMethod.GET, "/b/**").authenticated()
//        	.antMatchers("/boards/**").access("hasRole('ROLE_USER')")
//            .anyRequest().permitAll()
//            .and()
//        .formLogin()
//            .loginPage("/users/login")
//            .loginProcessingUrl("/users/login")
//            .permitAll()
//            .and()
//        .logout()
//            .permitAll()
//		.and().csrf().csrfTokenRepository(csrfTokenRepository())
//		.and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
//		.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    
	}
	
//	@Configuration
//	@EnableResourceServer
//	protected static class ResourceServerConfiguration
//			extends ResourceServerConfigurerAdapter {
//		@Override
//		public void configure(HttpSecurity http) throws Exception {
//			// @formatter:off
//			http
//				.antMatcher("/me")
//				.authorizeRequests().anyRequest().authenticated();
//			// @formatter:on
//		}
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public ResourceServerTokenServices userInfoTokenServices() {
		return new CustomResourceServerTokenServices(sso.getUserInfoUri(), sso.getClientId());
//		return new GitHubUserResourceServerTokenServices(sso.getUserInfoUri(), sso.getClientId());
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
//	@Bean
//	public FilterRegistrationBean oauth2ClientFilterRegistration(
//			OAuth2ClientContextFilter filter) {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(filter);
//		registration.setOrder(-100);
//		return registration;
//	}
////	
//	@Bean
//	@ConfigurationProperties("github")
//	ClientResources github() {
//		return new ClientResources();
//	}
//
//	@Bean
//	@ConfigurationProperties("facebook")
//	ClientResources facebook() {
//		return new ClientResources();
//	}
//	
//	@Bean
//	public GitHubUserResourceServerTokenServices grsts(ClientResources client) {
//		return new GitHubUserResourceServerTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
//	}
//
//	private Filter ssoFilter() {
//		CompositeFilter filter = new CompositeFilter();
//		List<Filter> filters = new ArrayList<>();
//		filters.add(ssoFilter(facebook(), "/login/facebook"));
//		filters.add(ssoFilter(github(), "/login/github"));
//		filter.setFilters(filters);
//		return filter;
//	}
//
//	private Filter ssoFilter(ClientResources client, String path) {
//		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter =
//				new OAuth2ClientAuthenticationProcessingFilter(path);
//		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(),
//				oauth2ClientContext);
//		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
//		GitHubUserResourceServerTokenServices tokenServices = grsts(client);
//		tokenServices.setRestTemplate(oAuth2RestTemplate);
//		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
//		return oAuth2ClientAuthenticationFilter;
//	}
//	
//	private Filter csrfHeaderFilter() {
//		return new OncePerRequestFilter() {
//			@Override
//			protected void doFilterInternal(HttpServletRequest request,
//					HttpServletResponse response, FilterChain filterChain)
//							throws ServletException, IOException {
//				CsrfToken csrf = (CsrfToken) request
//						.getAttribute(CsrfToken.class.getName());
//				if (csrf != null) {
//					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//					String token = csrf.getToken();
//					if (cookie == null
//							|| token != null && !token.equals(cookie.getValue())) {
//						cookie = new Cookie("XSRF-TOKEN", token);
//						cookie.setPath("/");
//						response.addCookie(cookie);
//					}
//				}
//				filterChain.doFilter(request, response);
//			}
//		};
//	}
//
//	private CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName("X-XSRF-TOKEN");
//		return repository;
//	}
}

class ClientResources {
	private OAuth2ProtectedResourceDetails client = new AuthorizationCodeResourceDetails();
	private ResourceServerProperties resource = new ResourceServerProperties();

	public OAuth2ProtectedResourceDetails getClient() {
		return client;
	}

	public ResourceServerProperties getResource() {
		return resource;
	}
}

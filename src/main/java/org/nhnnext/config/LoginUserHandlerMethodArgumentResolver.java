package org.nhnnext.config;

import org.nhnnext.domain.GitHubUserRepository;
import org.nhnnext.domain.TrelloUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserHandlerMethodArgumentResolver.class);
	
	@Autowired
	private GitHubUserRepository gitHubUserRepository;
	
	@Autowired
	private TrelloUserRepository trelloUserRepository;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated()) {
			return org.nhnnext.domain.User.GUEST_USER;
		}
		
		if (authentication instanceof OAuth2Authentication) {
			String principal = (String)authentication.getPrincipal();
			LOGGER.info("++++++++++++++++++++++++++++++");
			LOGGER.debug("login principal : {}", principal);
			return gitHubUserRepository.findByUserId(principal);
		} else {
			User principal = (User)authentication.getPrincipal();
			LOGGER.debug("login principal : {}", principal);
			return trelloUserRepository.findByUserId(principal.getUsername());
		}
	}
}

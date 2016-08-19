package org.nhnnext.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.nhnnext.domain.GitHubUser;
import org.nhnnext.domain.GitHubUserRepository;
import org.nhnnext.domain.TrelloUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

public class CustomResourceServerTokenServices extends UserInfoTokenServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomResourceServerTokenServices.class);
	
    @Autowired
	private GitHubUserRepository githubUserRepository;
	
	private final String userInfoEndpointUrl;

	private final String clientId;
	
	private OAuth2RestOperations restTemplate;
	
	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

//	public CustomResourceServerTokenServices() {
//		super(" "," ");
//	}
	
	public CustomResourceServerTokenServices(String userInfoEndpointUrl, String clientId) {
		super(userInfoEndpointUrl, clientId);
		this.userInfoEndpointUrl = userInfoEndpointUrl;
		this.clientId = clientId;
	}
	
	public void setRestTemplate(OAuth2RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Transactional
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		Map<String, Object> userInfo = getMap(this.userInfoEndpointUrl, accessToken);
		LOGGER.info("load authenticated: ");
		if (userInfo.containsKey("error")) {
			this.logger.debug("userinfo returned error: " + userInfo.get("error"));
			throw new InvalidTokenException(accessToken);
		}
		LOGGER.info("load authenticated 2 ");

		LOGGER.info("accessToken:" + accessToken);
//		
//		LOGGER.debug("access token : {}", accessToken);
		//org.nhnnext.domain.GitHubUser user = githubUserRepository.findByAccessToken(accessToken);
		//GitHubUser githubUser = githubUserRepository.findByAccessToken(accessToken);
		LOGGER.info("load authenticated: ");
//		if (githubUser != null) {
//			return extractAuthentication(userInfo);
//		}
		
		LOGGER.info("pass try to access database: ");
		LOGGER.info("load authenticated: ");
		
		GitHubUser githubUser = new GitHubUser(userInfo.get("login")+ " ", userInfo.get("email")+ " ", userInfo.get("name")+ " ", accessToken);
		LOGGER.info("generate database: "+ githubUser.getUserId());
		LOGGER.info("generate database: "+ githubUser.getEmail());
		LOGGER.info("generate database: "+ githubUser.getName());
		githubUserRepository.save(githubUser);
		LOGGER.info("save to access database: ");
//
//		Map<String, Object> userInfo = getUserInfo(this.userInfoEndpointUrl, accessToken);
//		LOGGER.debug("UserInfo : {}", userInfo);
//		if (userInfo.containsKey("error")) {
//			LOGGER.debug("userinfo returned error: " + userInfo.get("error"));
//			throw new InvalidTokenException(accessToken);
//		}
//		githubUser = new GitHubUser(userInfo.get("login") + "", userInfo.get("email") + "", userInfo.get("name") + "", accessToken);
//		githubUserRepository.save(githubUser);
//		//return extractAuthentication(githubUser);
//		
//		
//		
		
		return extractAuthentication(githubUser);
		//return extractAuthentication(userInfo);
	}
	

//	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
//		Object principal = getPrincipal(map);
//		List<GrantedAuthority> authorities = this.authoritiesExtractor
//				.extractAuthorities(map);
//		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null,
//				null, null, null, null);
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//				principal, "N/A", authorities);
//		token.setDetails(map);
//		return new OAuth2Authentication(request, token);
//	}

	private OAuth2Authentication extractAuthentication(GitHubUser githubUser) {
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null, null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(githubUser.getUserId(),
				"N/A", authorities);
		token.setDetails(githubUser);
		return new OAuth2Authentication(request, token);
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> getUserInfo(String path, String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(path, HttpMethod.GET, entity, Map.class).getBody();
	}
	
	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> getMap(String path, String accessToken) {
		this.logger.info("Getting user info from: " + path);
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
					.getAccessToken();
			if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
						accessToken);
				token.setTokenType(this.tokenType);
				restTemplate.getOAuth2ClientContext().setAccessToken(token);
			}
			return restTemplate.getForEntity(path, Map.class).getBody();
		}
		catch (Exception ex) {
			this.logger.info("Could not fetch user details: " + ex.getClass() + ", "
					+ ex.getMessage());
			return Collections.<String, Object>singletonMap("error",
					"Could not fetch user details");
		}
	}




}

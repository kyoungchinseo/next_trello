package org.nhnnext.service;

import java.util.List;
import java.util.Map;

import org.nhnnext.domain.GitHubUser;
import org.nhnnext.domain.GitHubUserRepository;
import org.nhnnext.domain.TrelloUser;
import org.nhnnext.domain.TrelloUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.repository.CrudRepository;


public class GitHubUserResourceServerTokenServices implements ResourceServerTokenServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(GitHubUserResourceServerTokenServices.class);
	
	@Autowired
	private GitHubUserRepository githubUserRepository;
	
	private final String userInfoEndpointUrl;

	private final String clientId;
	
	private static final String[] PRINCIPAL_KEYS = new String[] { "user", "username",
			"userid", "user_id", "login", "id", "name" };
	
	private OAuth2RestOperations restTemplate;

	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
	
	public GitHubUserResourceServerTokenServices(String userInfoEndpointUrl, String clientId) {
		this.userInfoEndpointUrl = userInfoEndpointUrl;
		this.clientId = clientId;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setRestTemplate(OAuth2RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
		this.authoritiesExtractor = authoritiesExtractor;
	}
	
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		LOGGER.info("access token : {}", accessToken);
		
		GitHubUser githubUser = null;
		//GitHubUser githubUser = githubUserRepository.findByAccessToken(accessToken);
		if (githubUser != null) {
			return extractAuthentication(githubUser);
		}

		Map<String, Object> userInfo = getUserInfo(this.userInfoEndpointUrl, accessToken);
		LOGGER.info("UserInfo : {}", userInfo);
		if (userInfo.containsKey("error")) {
			LOGGER.debug("userinfo returned error: " + userInfo.get("error"));
			throw new InvalidTokenException(accessToken);
		}
		githubUser = new GitHubUser(userInfo.get("login") + "", userInfo.get("email") + "", userInfo.get("name") + "", accessToken);
		githubUserRepository.save(githubUser);
		return extractAuthentication(githubUser);
	}

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

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}


}

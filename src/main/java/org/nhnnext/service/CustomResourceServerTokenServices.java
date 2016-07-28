package org.nhnnext.service;

import java.util.List;
import java.util.Map;

import org.nhnnext.domain.GitHubUser;
import org.nhnnext.domain.GitHubUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestTemplate;

public class CustomResourceServerTokenServices implements ResourceServerTokenServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomResourceServerTokenServices.class);
	
	@Autowired
	private GitHubUserRepository githubUserRepository;
	
	private final String userInfoEndpointUrl;

	private final String clientId;
	
	
	public CustomResourceServerTokenServices(String userInfoEndpointUrl, String clientId) {
		this.userInfoEndpointUrl = userInfoEndpointUrl;
		this.clientId = clientId;
	}
	
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		LOGGER.debug("access token : {}", accessToken);
		GitHubUser githubUser = githubUserRepository.findByAccessToken(accessToken);
		if (githubUser != null) {
			return extractAuthentication(githubUser);
		}

		Map<String, Object> userInfo = getUserInfo(this.userInfoEndpointUrl, accessToken);
		LOGGER.debug("UserInfo : {}", userInfo);
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

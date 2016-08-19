package org.nhnnext.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	argumentResolvers.add(loginUserHandlerMethodArgumentResolver());
    }
    
    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver() {
    	return new LoginUserHandlerMethodArgumentResolver();
    }
    
    
}

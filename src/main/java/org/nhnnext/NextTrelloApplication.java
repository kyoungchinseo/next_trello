package org.nhnnext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;



@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class NextTrelloApplication {
	
	private static final Logger log = LoggerFactory.getLogger(NextTrelloApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NextTrelloApplication.class, args);
	}
	
	
}

package org.nhnnext.web;

import org.nhnnext.domain.Card;
import org.nhnnext.domain.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class PageController {
	
	private static final Logger log = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	CardRepository cardRespository;
	
	@RequestMapping("/page")
	public String page() {
		cardRespository.save(new Card("new","new"));
		for(Card card: cardRespository.findAll()) {
			log.info(card.getId() + " " + card.getTitle());
		}
		return "page";
	}
}

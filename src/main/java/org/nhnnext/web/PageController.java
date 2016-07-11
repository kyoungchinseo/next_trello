package org.nhnnext.web;

import org.nhnnext.domain.Card;
import org.nhnnext.domain.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller 
public class PageController {
	
	private static final Logger log = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	CardRepository cardRespository;
	
	@RequestMapping(value="/d",method=RequestMethod.GET)
	public String page() {
		cardRespository.save(new Card("new","new"));
		for(Card card: cardRespository.findAll()) {
			log.info(card.getId() + " " + card.getTitle());
		}
		return "page";
	}
	
	@RequestMapping(value="/d/{id}", method=RequestMethod.GET)
	public String show(@PathVariable("id") long id, Model model) {
		log.info("id: " + id);
		cardRespository.save(new Card("new","new"));
		for(Card card: cardRespository.findAll()) {
			log.info(card.getId() + " " + card.getTitle());
		}
		model.addAttribute("card", cardRespository.findById(id));
		return "page";
	}
}

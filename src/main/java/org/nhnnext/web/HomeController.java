package org.nhnnext.web;

import org.nhnnext.domain.Board;
import org.nhnnext.domain.BoardForm;
import org.nhnnext.domain.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	
	@RequestMapping(value="/greeting", method=RequestMethod.GET)
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	
	
		

}
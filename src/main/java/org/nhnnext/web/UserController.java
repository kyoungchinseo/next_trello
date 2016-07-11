package org.nhnnext.web;

import org.nhnnext.domain.Board;
import org.nhnnext.domain.BoardForm;
import org.nhnnext.domain.BoardRepository;
import org.nhnnext.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@ModelAttribute
	BoardForm setUpForm() {
		return new BoardForm();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String autheticate(Model model) {
		log.info("count: " + boardRepository.count());
		if (boardRepository.count() <= 0 ) {
			boardRepository.save(new Board("First Project"));
			boardRepository.save(new Board("Second Project"));
		}
		log.info("count: " + boardRepository.count());
		model.addAttribute("boards",boardRepository.findAll());
		return "project";
	}
	
	@RequestMapping(value="/signUp", method=RequestMethod.GET)
	public String signup() {
		return "signUp";
	}
	
	@RequestMapping(value="/signUp", method=RequestMethod.POST)
	public String create() {
		return "signUp";
	}
	
	
}

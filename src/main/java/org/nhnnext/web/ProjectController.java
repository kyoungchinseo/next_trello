package org.nhnnext.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.nhnnext.domain.Board;
import org.nhnnext.domain.BoardForm;
import org.nhnnext.domain.BoardRepository;
import org.nhnnext.domain.Deck;
import org.nhnnext.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Data;

@Controller
public class ProjectController {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@ModelAttribute
	BoardForm setUpForm() {
		return new BoardForm();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String projectMain(@AuthenticationPrincipal Principal principal, Model model) {
		log.info("count: " + boardRepository.count());
		log.debug("login user : {}", principal);
		if (principal == null) {
			return "login";
		}
		
		if (boardRepository.count() <= 0 ) {
			boardRepository.save(new Board("First Project"));
			boardRepository.save(new Board("Second Project"));
		}
		log.info("count: " + boardRepository.count());
		model.addAttribute("boards",boardRepository.findAll());
		return "project";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(@RequestBody BoardForm boardForm, Model model) {
		log.info(boardForm.toString());
		log.info("title: " +boardForm.getTitle());
		boardRepository.save(new Board(boardForm.getTitle()));
		return "redirect:/";
	}
}

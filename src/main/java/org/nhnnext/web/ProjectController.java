package org.nhnnext.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.nhnnext.domain.Board;
import org.nhnnext.domain.BoardForm;
import org.nhnnext.domain.BoardRepository;
import org.nhnnext.domain.Deck;
import org.nhnnext.domain.GitHubUserRepository;
import org.nhnnext.domain.TrelloUserRepository;
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
	private TrelloUserRepository trelloUserRepository;
	
	@Autowired
	private GitHubUserRepository githubUserRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@ModelAttribute
	BoardForm setUpForm() {
		return new BoardForm();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String projectMain(@AuthenticationPrincipal Principal principal, Model model) {
		log.info("count: " + boardRepository.count());
		log.info("login user : {}", principal);
		if (principal == null) {
			return "login";
		}
		log.info(principal.getName());
		
//		public GitHubUser(String userId, String email, String name, String accessToken) {
//			super(userId, email);
//			this.name = name;
//			this.accessToken = accessToken;
//		}
		//githubUserRepository.save(new GitHubUser());
		// 여기서 데이터를 처리하면 된다. 
		// 소셜유저이던, 유저 데이터 던 저장된다. 
		// 문제는 유저 데이터는 데이터 베이스에 연결되는데 
		// 소셜데이터는 데이터 베이스에 저장이 안된다는 것 (그 부분공부하자)
		
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

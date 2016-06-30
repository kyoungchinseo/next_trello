package org.nhnnext.web;

import org.nhnnext.domain.Board;
import org.nhnnext.domain.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProjectController {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String projectMain(Model model) {
		log.info("count: " + boardRepository.count());
		if (boardRepository.count() <= 0 ) {
			boardRepository.save(new Board("First Project"));
			boardRepository.save(new Board("Second Project"));
		}
		log.info("count: " + boardRepository.count());
		model.addAttribute("boards",boardRepository.findAll());
		return "project";
	}
}

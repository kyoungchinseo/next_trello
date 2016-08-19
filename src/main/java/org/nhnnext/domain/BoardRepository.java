package org.nhnnext.domain;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository <Board, Long>{
	//@Cacheable("Boards")
	//@Cacheable("empcache") 
	List<Board> findAll();
}

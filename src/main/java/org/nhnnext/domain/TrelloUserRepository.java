package org.nhnnext.domain;

import org.springframework.data.repository.CrudRepository;

public interface TrelloUserRepository extends CrudRepository<TrelloUser,Long>{
	
	TrelloUser findByUserId(String userId);
}

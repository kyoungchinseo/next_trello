package org.nhnnext.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long>  {
	List<Card> findById(long id);
}

package org.nhnnext.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	org.nhnnext.domain.User findByUserName(String username);

}

package org.nhnnext.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	private long id;
	
	@Column(name="userName", length=50, nullable=false)
	private String userName;
	
	@Column(name="email", length=50, nullable=false)
	private String email;
	
	@Column(name="password", length=50,nullable=false)
	private String password;
	
}

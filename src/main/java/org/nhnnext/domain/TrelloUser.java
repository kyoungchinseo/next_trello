package org.nhnnext.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.data.annotation.Transient;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value = UserType.Values.TRELLO)
public class TrelloUser extends User {
	private String password;
	
	@Transient
	private String rawPassword;
	
	public TrelloUser() {
	}
	
	public TrelloUser(String userId, String email, String password) {
		super(userId, email);
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(rawPassword);
	}
}

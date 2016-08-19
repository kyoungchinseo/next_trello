package org.nhnnext.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Board implements Serializable {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BOARD_ID")
	private long id;
	
	@Column(name="title", length=100, nullable=false)
	private String title;
	
	@Column(name="createdDate", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToMany
	@JoinColumn(name="BOARD_ID")
	private List<Deck> decks;
	
	@OneToMany
	@JoinColumn(name="BOARD_ID")
	private List<User> members;

	public Board() {
		
	}
	
	public Board(String title) {
		this.title = title;
	}
	
	
	
	
	
	
}

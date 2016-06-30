package org.nhnnext.domain;

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
public class Deck {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DECK_ID")
	private long id;
	
	@Column(name="title", length=50, nullable=false)
	private String title;
	
	@Column(name="createdDate", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToMany
	@JoinColumn(name="DECK_ID")
	private List<Card> cards;
	
}

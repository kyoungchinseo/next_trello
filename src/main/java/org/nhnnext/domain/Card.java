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
public class Card {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CARD_ID")
	private long id;
	
	@Column(name="title", length=100, nullable=false)
	private String title;
	
	@Column(name="description", length=200, nullable=true)
	private String description;
	
	@Column(name="createdDate", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToMany
	@JoinColumn(name="CARD_ID")
	private List<Comment> comments;
	
	@OneToMany
	@JoinColumn(name="CARD_ID")
	private List<User> assignees;
	
	@OneToMany
	@JoinColumn(name="CARD_ID")
	private List<Attachment> attachedItems;
	
	@Temporal(TemporalType.DATE)
	private Date dueDate;
	
	@Temporal(TemporalType.TIME)
	private Date dueTime;
	
	
	protected Card() {}
	
	public Card(String title, String description) {
		this.title = title;
		this.description = description;
	}

}

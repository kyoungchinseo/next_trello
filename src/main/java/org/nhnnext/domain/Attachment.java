package org.nhnnext.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Attachment {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ATTACHMENT_ID")
	private long id;
	
	@Column(name="link")
	private String link;

	@Column(name="createdDate", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	
	
}

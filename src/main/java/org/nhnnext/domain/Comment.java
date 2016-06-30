package org.nhnnext.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Comment {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COMMENT_ID")
	private long id;
	
	@Column(name="content", length=200)
	private String content;
	
	@Column(name="createdDate", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
}

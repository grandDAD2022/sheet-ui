package com.github.grandDAD2022.sheet.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne()
	private User id_user;
	
	private String date;
	private String comment;
	
	protected Post() {}
	
	public Post(User id_user, String date, String comment) {
		this.id_user = id_user;
		this.date = date;
		this.comment = comment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getId_user() {
		return id_user;
	}

	public void setId_user(User id_user) {
		this.id_user = id_user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setPost(User user) {
		this.id_user = user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", id_user=" + id_user + ", date=" + date + ", comment=" + comment + "]";
	}
	
}

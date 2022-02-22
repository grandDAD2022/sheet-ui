package com.github.grandDAD2022.sheet.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private long id_user;
	private String date;
	private String content;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comment = new ArrayList<Comment> ();
	
	protected Post() {}
	
	public Post(long id_user, String date, String content) {
		this.id_user = id_user;
		this.date = date;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId_user() {
		return id_user;
	}

	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public void addComment(Comment c) {
		this.comment.add(c);
		c.setPost(this);
	}
	
	public void removeComment(Comment c) {
		this.comment.remove(c);
		c.setPost(null);
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", id_user=" + id_user + ", date=" + date + ", comment=" + comment + "]";
	}
	
}

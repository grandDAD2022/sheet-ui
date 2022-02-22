package com.github.grandDAD2022.sheet.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PUBLICACION", nullable = false, unique = true)
	private long id;
	
	@Column(name = "FECHA_PUBLICACION", nullable = false)
	private String date;
	
	@Column(name = "TEXTO_PUBLICACION", nullable = false)
	private String content;
	
	@ManyToOne
	@JoinColumn
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comment = new ArrayList<Comment> ();
	
	protected Post() {}
	
	public Post(String date, String content) {
		this.user = null;
		this.date = date;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "Post [id=" + id + ", id_user=" + user + ", date=" + date + ", comment=" + comment + "]";
	}
	
}

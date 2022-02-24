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
	@JoinColumn(name = "ID_USUARIO")
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comment = new ArrayList<Comment> ();
	
	@ManyToOne
	@JoinColumn(name = "ID_COMUNIDAD")
	@JsonBackReference
	private Community community;
	
	protected Post() {}
	
	public Post(String date, String content) {
		this.user = null;
		this.date = date;
		this.content = content;
		this.community = null;
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
	
	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", date=" + date + ", content=" + content + ", user=" + user + ", comment=" + comment
				+ ", community=" + community + "]";
	}
	
}

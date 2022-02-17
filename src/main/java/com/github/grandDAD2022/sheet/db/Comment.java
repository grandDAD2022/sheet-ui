package com.github.grandDAD2022.sheet.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String comment_date;
	private String content;
	private Post post;
	private User author;
	private List<Comment> answer;
	
	protected Comment () {}
 	
	public Comment(String comment_date, String content, Post post, User author, List<Comment> answer) {
		this.comment_date = comment_date;
		this.content = content;
		this.post = post;
		this.author = author;
		this.answer = answer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment_date() {
		return comment_date;
	}

	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Comment> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Comment> answer) {
		this.answer = answer;
	}
	
}

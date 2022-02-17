package com.github.grandDAD2022.sheet.db;

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
	private String post;
	private String author;
	private String answer;
	
	protected Comment () {}
 	
	public Comment(String comment_date, String content, String String, String author, String answer) {
		this.comment_date = comment_date;
		this.content = content;
		this.post = String;
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

	public String getString() {
		return post;
	}

	public void setString(String String) {
		this.post = String;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment_date=" + comment_date + ", content=" + content + ", post=" + post
				+ ", author=" + author + ", answer=" + answer + "]";
	}
	
	
}

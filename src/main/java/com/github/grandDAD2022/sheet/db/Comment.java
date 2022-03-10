package com.github.grandDAD2022.sheet.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Comment {
	public interface Basic {}
	public interface Autor {}
	public interface InPost {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_COMENTARIO", nullable = false, unique = true)
	@JsonView(Basic.class)
	private long id;
	
	@Column(name = "FECHA_COMENTARIO", nullable = false)
	@JsonView(Basic.class)
	private String comment_date;
	
	@Column(name = "TEXTO_COMENTARIO", nullable = false)
	@JsonView(Basic.class)
	private String content;
	
	@Column(name = "RESPUESTA", nullable = true)
	@JsonView(Basic.class)
	private String answer;
	
	@ManyToOne
	@JoinColumn(name = "ID_POST", nullable = false)
	@JsonView(InPost.class)
	private Post post;
	
	@ManyToOne()
	@JoinColumn(name="ID_AUTOR", nullable = false)
	@JsonView(Autor.class)
	private User author;
	
	protected Comment () {}
 	
	public Comment(String comment_date, String content, String answer) {
		this.comment_date = comment_date;
		this.content = content;
		this.author = null;
		this.answer = answer;
		this.post = null;
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment_date=" + comment_date + ", content=" + content + ", answer=" + answer
				+ ", post=" + post + ", author=" + author + "]";
	}
	
	
}
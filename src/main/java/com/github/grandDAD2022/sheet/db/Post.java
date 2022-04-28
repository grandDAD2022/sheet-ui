package com.github.grandDAD2022.sheet.db;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "IMAGEN", nullable = true)
	private String image;
	
	@Lob
	@JsonIgnore
	private Blob imageFile;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@Column(name = "COMENTARIOS", nullable = true)
	@JsonIgnore
	private List<Comment> comment = new ArrayList<Comment> ();
	
	@ManyToOne
	@JoinColumn(name = "ID_COMUNIDAD", nullable = true)
	@JsonIgnore
	private Community community;
	
	protected Post() {}
	
	public Post(String date, String content) {
		this.user = null;
		this.date = date;
		this.content = content;
		this.community = null;
		this.image = null;
		this.imageFile = null;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Blob getImageFile() {
		return imageFile;
	}

	public void setImageFile(Blob imageFile) {
		this.imageFile = imageFile;
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
	public int hashCode() {
		return Objects.hash(comment, community, content, date, id, image, imageFile, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(community, other.community)
				&& Objects.equals(content, other.content) && Objects.equals(date, other.date) && id == other.id
				&& Objects.equals(image, other.image) && Objects.equals(imageFile, other.imageFile)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", date=" + date + ", content=" + content + ", image=" + image + ", imageFile="
				+ imageFile + ", user=" + user + ", comment=" + comment + ", community=" + community + "]";
	}

	
}
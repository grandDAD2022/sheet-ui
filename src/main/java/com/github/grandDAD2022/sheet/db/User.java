package com.github.grandDAD2022.sheet.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USUARIO", nullable = false, unique = true)
	private long id;
	
	@Column(name = "NOMBRE_PERSONAL", nullable = false)
	private String firstName;
	
	@Column(name = "APELLIDO", nullable = false)
	private String surname;
	
	@Column(name = "MAIL", nullable = false)
	private String e_mail;
	
	@Column(name = "FECHA_NACIMIENTO", nullable = false)
	private String date_birth;
	
	@Column(name = "N_TELEFONO", nullable = false)
	private String tl_number;
	
	@Column(name = "BIOGRAFIA", nullable = true)
	private String bio;
	
	@Column(name = "NOMBRE_USUARIO", nullable = false)
	private String username;
	
	@Column(name = "CONTRASEÑA", nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "admin_user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Community> com_admin = new ArrayList<Community> ();
	
	@ManyToMany
	private List<Community> communities = new ArrayList<Community> ();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<Post> posts = new ArrayList<Post> ();
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private List<Comment> comments = new ArrayList<Comment> ();
	
	protected User () {}
	
	public User(String firstName, String surname, String e_mail, String date_birth, String tl_number,
			String bio, String username, String password) {
		this.firstName = firstName;
		this.surname = surname;
		this.e_mail = e_mail;
		this.date_birth = date_birth;
		this.tl_number = tl_number;
		this.bio = bio;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getDate_birth() {
		return date_birth;
	}

	public void setDate_birth(String date_birth) {
		this.date_birth = date_birth;
	}

	public String getTl_number() {
		return tl_number;
	}

	public void setTl_number(String tl_number) {
		this.tl_number = tl_number;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Community> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void addPost (Post p) {
		this.posts.add(p);
		p.setUser(this);
	}
	
	public void removePost (Post p) {
		this.posts.remove(p);
		p.setUser(null);
	}
	
	public List<Community> getCom_admin() {
		return com_admin;
	}

	public void setCom_admin(List<Community> com_admin) {
		this.com_admin = com_admin;
	}
	
	public void createCommunity (Community c) {
		this.com_admin.add(c);
		c.setAdmin_user(this);
	}
	
	public void removeCommunity (Community c) {
		this.com_admin.remove(c);
		c.setAdmin_user(null);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void createNewComment (Comment c) {
		this.comments.add(c);
		c.setAuthor(this);
	}
	
	public void removeComment (Comment c) {
		this.comments.remove(c);
		c.setAuthor(null);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", surname=" + surname + ", e_mail=" + e_mail
				+ ", date_birth=" + date_birth + ", tl_number=" + tl_number + ", bio=" + bio + "]";
	}
	
	
}

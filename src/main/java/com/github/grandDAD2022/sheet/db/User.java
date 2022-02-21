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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String firstName;
	private String surname;
	private String e_mail;
	private String date_birth;
	private String tl_number;
	private String bio;
	private String username;
	private String password;
	
	@OneToMany(mappedBy = "id_user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "admin_user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Community> communities_created;
	
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
		this.posts = new ArrayList<Post> ();
		this.communities_created = new ArrayList<Community> ();
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
/*
	public void addPost (Post p) {
		this.posts.add(p);
		p.setPost(this);
	}
	
	public void removePost (Post p) {
		this.posts.remove(p);
		p.setPost(null);
	}
	
	public List<Community> getCommunities_created() {
		return communities_created;
	}

	public void setCommunities_created(List<Community> communities_created) {
		this.communities_created = communities_created;
	}
*/
	public void addCommunity_created (Community c) {
		this.communities_created.add(c);
		c.setAdmin_user(this);
	}
	
	public void removeCommunity (Community c) {
		this.communities_created.remove(c);
		c.setAdmin_user(null);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", surname=" + surname + ", e_mail=" + e_mail
				+ ", date_birth=" + date_birth + ", tl_number=" + tl_number + ", bio=" + bio + "]";
	}
	
	
}

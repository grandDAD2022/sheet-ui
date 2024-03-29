package com.github.grandDAD2022.sheet.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Community {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_COMUNIDAD", nullable = false, unique = true)
	private long id;
	
	@Column(name = "NOMBRE_COMUNIDAD", nullable = false)
	private String name;
	
	@Column(name = "FECHA_CREACION", nullable = false)
	private String creation_date;
	
	@Column(name = "DESCRIPCION_COMUNIDAD", nullable = true)
	private String comm_description;
	
	@ManyToMany()
	@Column(name = "USUARIOS_EN_COMUNIDAD", nullable = false)
	@JsonIgnore
	private List<User> user_in_community = new ArrayList<User> ();
	
	@ManyToOne
	@JoinColumn(name = "ID_ADMINISTRADOR", nullable = false)
	@JsonIgnore
	private User admin_user;
	
	@OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@Column(name = "POSTS", nullable = true)
	@JsonIgnore
	private List<Post> posts = new ArrayList<Post> ();

	protected Community() {}
	
	public Community(String name,String comm_description) {
		this.name = name;
		this.admin_user = null;
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String today = formatter.format(date);
		this.creation_date = today;
		this.comm_description = comm_description;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAdmin_user() {
		return admin_user;
	}

	public void setAdmin_user(User admin_user) {
		this.admin_user = admin_user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getComm_description() {
		return comm_description;
	}

	public void setComm_description(String comm_description) {
		this.comm_description = comm_description;
	}

	public List<User> getUser_in_community() {
		return user_in_community;
	}

	public void setUser_in_community(List<User> user_in_community) {
		this.user_in_community = user_in_community;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public void addPost (Post p) {
		this.posts.add(p);
		p.setCommunity(this);
	}
	
	public void removePost (Post p) {
		this.posts.remove(p);
		p.setCommunity(null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(admin_user, comm_description, creation_date, id, posts, user_in_community);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Community other = (Community) obj;
		return Objects.equals(admin_user, other.admin_user) && Objects.equals(comm_description, other.comm_description)
				&& Objects.equals(creation_date, other.creation_date) && id == other.id
				&& Objects.equals(posts, other.posts) && Objects.equals(user_in_community, other.user_in_community);
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", creation_date=" + creation_date + ", comm_description=" + comm_description
				+ ", user_in_community=" + user_in_community + ", admin_user=" + admin_user + ", posts=" + posts + "]";
	}

}
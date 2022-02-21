package com.github.grandDAD2022.sheet.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String creation_date;
	private String comm_description;
	private String user_in_community;
	
	@ManyToOne()
	private User admin_user;
	
	protected Community() {}
	
	public Community(String creation_date, String comm_description, String user_in_community) {
		this.admin_user = null;
		this.creation_date = creation_date;
		this.comm_description = comm_description;
		this.user_in_community = user_in_community;
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

	public String getUser_in_community() {
		return user_in_community;
	}

	public void setUser_in_community(String user_in_community) {
		this.user_in_community = user_in_community;
	}
	
}

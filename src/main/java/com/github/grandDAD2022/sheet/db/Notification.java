package com.github.grandDAD2022.sheet.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String notify_type;
	private String notify_date;
	private String notify_text;
	
	protected Notification () {}

	public Notification(String notify_type, String notify_date, String notify_text) {
		this.notify_type = notify_type;
		this.notify_date = notify_date;
		this.notify_text = notify_text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_date() {
		return notify_date;
	}

	public void setNotify_date(String notify_date) {
		this.notify_date = notify_date;
	}

	public String getNotify_text() {
		return notify_text;
	}

	public void setNotify_text(String notify_text) {
		this.notify_text = notify_text;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", notify_type=" + notify_type + ", notify_date=" + notify_date
				+ ", notify_text=" + notify_text + "]";
	}
	
	
}

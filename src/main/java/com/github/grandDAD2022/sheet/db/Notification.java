package com.github.grandDAD2022.sheet.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_NOTIFICACION", nullable = false, unique = true)
	private long id;
	
	@Column(name = "TIPO_NOTIFICACION", nullable = false)
	private String notify_type;
	
	@Column(name = "FECHA_NOTIFICACION", nullable = false)
	private String notify_date;
	
	@Column(name = "TEXTO_NOTIFICACION", nullable = false)
	private String notify_text;
	
	@ManyToMany()
	@Column(name = "NOTIFICACION_USUARIO", nullable = false)
	@JsonIgnore
	private List<User> user_notification = new ArrayList<User> ();
	
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
	
	public List<User> getUser_notification() {
		return user_notification;
	}

	public void setUser_notification(List<User> user_notification) {
		this.user_notification = user_notification;
	}

	public void newNotify(User u) {
		u.getNotification().add(this);
		this.getUser_notification().add(u);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, notify_date, notify_text, notify_type, user_notification);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		return id == other.id && Objects.equals(notify_date, other.notify_date)
				&& Objects.equals(notify_text, other.notify_text) && Objects.equals(notify_type, other.notify_type)
				&& Objects.equals(user_notification, other.user_notification);
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", notify_type=" + notify_type + ", notify_date=" + notify_date
				+ ", notify_text=" + notify_text + ", user_notification=" + user_notification + "]";
	}

}

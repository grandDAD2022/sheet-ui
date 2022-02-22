package com.github.grandDAD2022.sheet.controllers;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.grandDAD2022.sheet.db.Notification;
import com.github.grandDAD2022.sheet.db.NotificationRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notification", description = "API de notificaciones")
public class NotificationController {

	@Autowired
	private NotificationRepository notifications;
	
	@PostConstruct
	public void init() {
		// TODO: no inicializar cuenta alguna
		if (notifications.findAll().isEmpty())
			notifications.save(new Notification("1", "17-02-2022", "primera notificacion"));
	}
	
	@GetMapping("/")
	@Operation(summary = "Obtener una lista con todas las notificaciones")
	public Collection<Notification> getNotifications() {
		return notifications.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener una lista de notificaciones con un id")
	public Notification getNotification(@PathVariable long id) {
		return notifications.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear una notificación")
	public Notification createNotification(@RequestBody Notification notification) {
		notifications.save(notification);
		return notification;
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Destruir una notificación")
	public Notification deleteNotification(@PathVariable long id) {
		Notification notification = notifications.findById(id).orElseThrow();
		notifications.deleteById(id);
		return notification;
	}
	
	@DeleteMapping("/")
	@Operation(summary = "Destruir todas las notificaciones")
	public void deleteNotifications() {
		notifications.deleteAll();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar una publicación")
	public Notification updateNotification(@PathVariable long id, @RequestBody Notification newNotification) {
		notifications.findById(id).orElseThrow();
		newNotification.setId(id);
		notifications.save(newNotification);
		
		return newNotification;
	}
}

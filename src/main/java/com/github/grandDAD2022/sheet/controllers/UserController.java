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

import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.userRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private userRepository users;
	
	@PostConstruct
	public void init() {
		users.save(new User("Rubén", "Vicente", "ruben@email.com", "09-02-2001", "699999999", "Hola!", "RubBen_19", "password"));
	}
	
	@GetMapping("/users")
	public Collection<User> getUsers() {
		return users.findAll();
	}
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable long id) {
		
		return users.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	public User createUser(@RequestBody User user) {
		
		users.save(user);
		
		return user;
	}
	
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable long id) {
		
		User user = users.findById(id).orElseThrow();
		users.deleteById(id);
		return user;
	}
	
	@DeleteMapping("/")
	public void deleteUsers() {
		users.deleteAll();
	}
	
	@PutMapping("/users/{id}")
	public User replaceUser(@PathVariable long id, @RequestBody User newUser) {
		users.findById(id);
		newUser.setId(id);
		users.save(newUser);
		
		return newUser;
	}
 }

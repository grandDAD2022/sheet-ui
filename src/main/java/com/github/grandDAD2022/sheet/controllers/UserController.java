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

import com.github.grandDAD2022.sheet.db.Comment;
import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "API de usuarios")
public class UserController {

	@Autowired
	private UserRepository users;
	
	@Autowired
	private CommunityRepository communities;
	
	@PostConstruct
	public void init() {
		if (users.findAll().isEmpty()) {
			User s0 = new User("Rubén", "Vicente", "ruben@email.com", "09-02-2001", "699999999", "Hola!", "RubBen_19", "password");
			User s1 = new User("Pepe", "Martín", "pepe@mail.es", "04-12-1992", "612345789", "Hi!", "pepe92", "pass");
			Community c = new Community("19-02-2022", "First community");
			communities.save(c);
			s0.getCommunities().add(c);
			s1.getCommunities().add(c);
			Post p = new Post("21-02-2021", "Primer post");
			p.addComment(new Comment("21-02-2022", "Primer Comentario :D", "Pepe", null));
			p.addComment(new Comment("21-02-2022", "Segundo Comentario :D", "Timmy", null));			
			s0.addPost(p);			
			users.save(s0);
			users.save(s1);
		}
	}
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de todos los usuarios")
	public Collection<User> getUsers() {
		return users.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener lista de usuarios a partir de una id")
	public User getUser(@PathVariable long id) {
		return users.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear un usuario")
	public User createUser(@RequestBody User user) {
		users.save(user);
		return user;
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Destruir un usuario por su id")
	public User deleteUser(@PathVariable long id) {
		User user = users.findById(id).orElseThrow();
		users.deleteById(id);
		return user;
	}
	
	@DeleteMapping("/")
	@Operation(summary = "Destruir todos los usuarios")
	public void deleteUsers() {
		users.deleteAll();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar un usuario partiendo de su id")
	public User updateUser(@PathVariable long id, @RequestBody User newUser) {
		users.findById(id).orElseThrow();
		newUser.setId(id);
		users.save(newUser);
		
		return newUser;
	}
 }

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
import com.github.grandDAD2022.sheet.db.CommentRepository;
import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
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
	private PostRepository posts;
	
	@Autowired
	private CommentRepository comments;
	
	@PostConstruct
	public void init() {
		if (users.findAll().isEmpty()) {
		//Creamos los usuarios que estarán en la base de datos
			User s0 = new User("Rubén", "Vicente", "ruben@email.com", "09-02-2001", "699999999", "Hola!", "RubBen_19", "password");
			User s1 = new User("Pepe", "Martín", "pepe@mail.es", "04-12-1992", "612345789", "Hi!", "pepe92", "pass");
		//Creamos un post
			Post p0 = new Post("21-02-2021", "Primer post");
			Post p1 = new Post("19-02-2022", "Segundo post");
		//Creamos comentarios	
			Comment c0 = new Comment("22-06-2023", "Primer comentario", null);
			Comment c1 = new Comment("26-02-2022", "Holaaa!", null);
			Comment c2 = new Comment("01-01-2023", "Feliz año!", null);
		//Añadimos los posts a los usuarios
			s0.addPost(p0);
			s1.addPost(p1);
		//Añadimos los comentarios a los usuarios
			s0.createNewComment(c0);
			s0.createNewComment(c2);
			s1.createNewComment(c1);
		//Guardamos los usuarios en el repositorio
			users.save(s0);
			users.save(s1);
		//Añadimos los comentarios al post
			p0.addComment(c0);
			p0.addComment(c1);
			p1.addComment(c2);
		//Guardamos el post
			posts.save(p0);
			posts.save(p1);
		//Guardamos los comentarios
			comments.save(c0);
			comments.save(c1);
			comments.save(c2);
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

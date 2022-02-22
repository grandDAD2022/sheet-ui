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
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post", description = "API de publicaciones")
public class PostController {

	@Autowired
	private PostRepository posts;
	
	@PostConstruct
	public void init() {
		// TODO: no inicializar cuenta alguna
		if (posts.findAll().isEmpty()) {
			Post p = new Post(0, "21-02-2021", "Primer post");
			p.addComment(new Comment("21-02-2022", "Primer Comentario :D", "Pepe", null));
			p.addComment(new Comment("21-02-2022", "Segundo Comentario :D", "Timmy", null));
			posts.save(p);
		}
	}
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de todas las publicaciones")
	public Collection<Post> getposts() {
		return posts.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener lista de las publicaciones con un id")
	public Post getpost(@PathVariable long id) {
		return posts.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear una publicación")
	public Post createpost(@RequestBody Post post) {
		posts.save(post);
		return post;
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Destruir una publicación")
	public Post deletepost(@PathVariable long id) {
		Post post = posts.findById(id).orElseThrow();
		posts.deleteById(id);
		return post;
	}
	
	@DeleteMapping("/")
	@Operation(summary = "Destruir todas las publicaciones")
	public void deleteposts() {
		posts.deleteAll();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar una publicación")
	public Post updatepost(@PathVariable long id, @RequestBody Post newpost) {
		posts.findById(id).orElseThrow();
		newpost.setId(id);
		posts.save(newpost);
		
		return newpost;
	}
 }

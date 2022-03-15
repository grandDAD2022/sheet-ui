package com.github.grandDAD2022.sheet.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.grandDAD2022.sheet.db.Comment;
import com.github.grandDAD2022.sheet.db.CommentRepository;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment", description = "API de comentarios")
public class CommentController {
	
	@Autowired
	private CommentRepository comments;
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private PostRepository posts;
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de comentarios")
	public Collection<Comment> getComments() {
		return comments.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener lista de comentarios a partir de una id")
	public Comment getComment(@PathVariable long id) {
		return comments.findById(id).orElseThrow();
	}
		
	@PostMapping("/")
	@Operation(summary = "Crear un comentario")
	public Comment createComment  (@RequestBody Comment c, @RequestParam long idAuthor, @RequestParam long idPost) {
		users.findById(idAuthor).orElseThrow();
		posts.findById(idPost).orElseThrow();
		User u = users.getById(idAuthor);
		Post p = posts.getById(idPost);
		u.createNewComment(c);
		p.addComment(c);
		comments.save(c);
		return c;
	}
	
	@DeleteMapping("/{id}") 
	@Operation(summary = "Destruir un comentario")
	public Comment deleteComment (@PathVariable long id) {
		Comment c = comments.findById(id).orElseThrow();
		comments.deleteById(id);
		return c;
	}
	
	@DeleteMapping ("/") 
	@Operation(summary = "Destruir todos los comentarios")
	public void deleteComments () {
		comments.deleteAll();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar un comentario")
	public Comment updateComment (@PathVariable long id, @RequestBody Comment newComment) {
		comments.findById(id).orElseThrow();
		Comment c = comments.getById(id);
		newComment.setId(id);
		newComment.setAuthor(c.getAuthor());
		newComment.setPost(c.getPost());
		comments.save(newComment);
		
		return newComment;
	}
}
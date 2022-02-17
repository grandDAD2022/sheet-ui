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
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private CommentRepository comments;
	@Autowired
	private UserRepository users;
	@Autowired
	private PostRepository posts;
	
	@PostConstruct
	public void init () {
		if(comments.findAll().isEmpty()) {
			Post p = new Post(0, "17-02-2022", "Primer post");
			User s = new User("Pepe", "Martín", "pepe@email.es", "03-05-1999", "678123456", "Soy pepe", "pepe1991", "pass");
			users.save(s);
			posts.save(p);
			comments.save(new Comment("17-02-2022", "Primer comentario", p, s, null));
		}
	}
	
	@GetMapping("/")
	public Collection<Comment> getComments() {
		return comments.findAll();
	}
	
	@GetMapping("/{id}")
	public Comment getComment(@PathVariable long id) {
		return comments.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	public Comment createComment  (@RequestBody Comment c) {
		comments.save(c);
		return c;
	}
	
	@DeleteMapping("/{id}") 
	public Comment deleteComment (@PathVariable long id) {
		Comment c = comments.findById(id).orElseThrow();
		comments.deleteById(id);
		return c;
	}
	
	@DeleteMapping ("/") 
	public void deleteComments () {
		comments.deleteAll();
	}
	
	
	@PutMapping("/{id}")
	public Comment updateComment (@PathVariable long id, @RequestBody Comment newComment) {
		comments.findById(id);
		newComment.setId(id);
		comments.save(newComment);
		
		return newComment;
	}
}

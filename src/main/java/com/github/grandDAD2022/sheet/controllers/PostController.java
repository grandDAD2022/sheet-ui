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
import org.springframework.web.bind.annotation.RestController;

import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;

// TODO: documentar	
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostRepository posts;
	
	@GetMapping("/")
	public Collection<Post> getposts() {
		return posts.findAll();
	}
	
	@GetMapping("/{id}")
	public Post getpost(@PathVariable long id) {
		return posts.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	public Post createpost(@RequestBody Post post) {
		posts.save(post);
		return post;
	}
	
	@DeleteMapping("/{id}")
	public Post deletepost(@PathVariable long id) {
		Post post = posts.findById(id).orElseThrow();
		posts.deleteById(id);
		return post;
	}
	
	@DeleteMapping("/")
	public void deleteposts() {
		posts.deleteAll();
	}
	
	@PutMapping("/{id}")
	public Post updatepost(@PathVariable long id, @RequestBody Post newpost) {
		posts.findById(id).orElseThrow();
		newpost.setId(id);
		posts.save(newpost);
		
		return newpost;
	}
 }

package com.github.grandDAD2022.sheet.controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.grandDAD2022.sheet.db.Comment;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post", description = "API de publicaciones")
public class PostController {
	interface PostDetails extends Post.Basic,Post.Comentarios, Comment.Basic {}
	@Autowired
	private PostRepository posts;
	
	@JsonView(PostDetails.class)
	@GetMapping("/")
	@Operation(summary = "Obtener lista de todas las publicaciones")
	public Collection<Post> getposts() {
		return posts.findAll();
	}
	
	@JsonView(PostDetails.class)
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
	
	@PostMapping("/{id}/image")
	public ResponseEntity<Object> uploadIamge(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		Post post = posts.findById(id).orElseThrow();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		post.setImage(location.toString());
		post.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
		posts.save(post);
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/{id}/image")
	public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException{
		Post post = posts.findById(id).orElseThrow();
		if (post.getImageFile() != null) {
			Resource file = new InputStreamResource(post.getImageFile().getBinaryStream());			
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").contentLength(post.getImageFile().length()).body(file);
		}
		else 
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}/image")
	public ResponseEntity<Object> deleteImage(@PathVariable long id) throws IOException {
		Post post = posts.findById(id).orElseThrow();
		post.setImage(null);
		post.setImageFile(null);
		posts.save(post);
		return ResponseEntity.noContent().build();
	}
 }
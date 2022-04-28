package com.github.grandDAD2022.sheet.controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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

import com.github.grandDAD2022.sheet.db.Comment;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post", description = "API de publicaciones")
public class PostController {
	
	@Autowired
	private PostRepository posts;
	
	@Autowired
	private UserRepository users;
	
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
	
	@GetMapping("/{id}/comments")
	@Operation(summary = "Obtener lista de comentarios de un post a partir de su id")
	public Collection<Comment> getPostComments (@PathVariable long id) {
		posts.findById(id).orElseThrow();
		Post p = posts.getById(id);
		return p.getComment();
	}
	
	@GetMapping("/{id}/author")
	@Operation(summary = "Obtener el autor a partir del id del post")
	public User getAuthor (@PathVariable long id) {
		posts.findById(id).orElseThrow();
		return posts.getById(id).getUser();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear una publicación")
	@CacheEvict
	public Post createpost(@RequestBody Post post, @RequestParam long idAuthor) {
		users.findById(idAuthor).orElseThrow();
		User u = users.getById(idAuthor);
		post.setUser(u);
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
	@CachePut
	public Post updatepost(@PathVariable long id, @RequestBody Post newpost) {
		posts.findById(id).orElseThrow();
		newpost.setId(id);
		newpost.setUser(posts.getById(id).getUser());
		posts.save(newpost);
		
		return newpost;
	}
	
	@PostMapping("/{id}/image")
	public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
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
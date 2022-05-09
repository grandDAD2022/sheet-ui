package com.github.grandDAD2022.sheet.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/community")
@Tag(name = "Community", description = "API de comunidades")
public class CommunityController {

	@Autowired
	private CommunityRepository communities;
	
	@Autowired
	private UserRepository users;
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de comunidades")
	public Collection<Community> getCommunities() {
		return communities.findAll();
	}
	
	@Cacheable(cacheNames = "comunidades", condition = "#id > 1")
	@GetMapping("/{id}")
	@Operation(summary = "Obtener una comunidad a partir de una id")
	public Community getCommunity(@PathVariable long id) {
		return communities.findById(id).orElseThrow();
	}
	
	@GetMapping("/{id}/admin")
	@Operation(summary = "Obtener el administrador de una comunidad a partir de su id")
	public User getCommunityAdmin(@PathVariable long id) {
		communities.findById(id).orElseThrow();
		return communities.getById(id).getAdmin_user();
	}
	
	@GetMapping("/{id}/users")
	@Operation(summary = "Obtener lista de usuarios de una comunidad a partir de su id")
	public Collection<User> getCommunityUsers(@PathVariable long id) {
		communities.findById(id).orElseThrow();
		return communities.getById(id).getUser_in_community();
	}
	
	@GetMapping("/{id}/posts")
	@Operation(summary = "Obtener lista de posts de una comunidad a partir de su id")
	public Collection<Post> getCommunityPosts(@PathVariable long id) {
		communities.findById(id).orElseThrow();
		return communities.getById(id).getPosts();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear una comunidad")
	public Community createCommunity  (@RequestBody Community c, @RequestParam long idAdmin) {
		users.findById(idAdmin).orElseThrow();
		User u = users.getById(idAdmin);
		u.createCommunity(c);
		communities.save(c);
		return c;
	}
	
	@DeleteMapping("/{id}") 
	@Operation(summary = "Destruir una comunidad")
	public Community deleteCommunity (@PathVariable long id) {
		Community c = communities.findById(id).orElseThrow();
		communities.deleteById(id);
		return c;
	}
	
	@DeleteMapping ("/") 
	@Operation(summary = "Destruir todas las comunidades")
	public void deleteCommunities () {
		communities.deleteAll();
	}
	
	@CachePut(cacheNames="comunidades", key="#dtoRequest.id")
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar una comunidad")
	public Community updateCommunity (@PathVariable long id, @RequestBody Community newCommunity) {
		communities.findById(id).orElseThrow();
		Community c = communities.getById(id);
		newCommunity.setId(id);
		newCommunity.setAdmin_user(c.getAdmin_user());
		communities.save(newCommunity);
		
		return newCommunity;
	}
}

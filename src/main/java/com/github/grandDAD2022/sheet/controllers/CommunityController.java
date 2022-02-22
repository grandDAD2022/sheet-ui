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

import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/community")
@Tag(name = "Communityt", description = "API de comunidades")
public class CommunityController {

	@Autowired
	private CommunityRepository communities;
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de comunidades")
	public Collection<Community> getCommunities() {
		return communities.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener lista de comunidades a partir de una id")
	public Community getCommunity(@PathVariable long id) {
		return communities.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear una comunidad")
	public Community createCommunity  (@RequestBody Community c) {
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
	
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar una comunidad")
	public Community updateComment (@PathVariable long id, @RequestBody Community newCommunity) {
		communities.findById(id).orElseThrow();
		newCommunity.setId(id);
		communities.save(newCommunity);
		
		return newCommunity;
	}
}

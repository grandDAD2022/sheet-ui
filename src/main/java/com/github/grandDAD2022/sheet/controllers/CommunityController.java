package com.github.grandDAD2022.sheet.controllers;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
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

@RestController
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CommunityRepository communities;
	
	@GetMapping("/")
	public Collection<Community> getCommunities() {
		return communities.findAll();
	}
	
	@GetMapping("/{id}")
	public Community getCommunity(@PathVariable long id) {
		return communities.findById(id).orElseThrow();
	}
	
	@PostMapping("/")
	public Community createCommunity  (@RequestBody Community c) {
		communities.save(c);
		return c;
	}
	
	@DeleteMapping("/{id}") 
	public Community deleteCommunity (@PathVariable long id) {
		Community c = communities.findById(id).orElseThrow();
		Hibernate.initialize(c.getAdmin_user());
		communities.deleteById(id);
		return c;
	}
	
	@DeleteMapping ("/") 
	public void deleteCommunities () {
		communities.deleteAll();
	}
	
	
	@PutMapping("/{id}")
	public Community updateComment (@PathVariable long id, @RequestBody Community newCommunity) {
		communities.findById(id).orElseThrow();
		newCommunity.setId(id);
		communities.save(newCommunity);
		
		return newCommunity;
	}
}

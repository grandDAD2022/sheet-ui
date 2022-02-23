package com.github.grandDAD2022.sheet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

@RequestMapping("/profile")
@Controller
public class ProfileController {
	@Autowired
	private UserRepository userRepo;
	
	// TODO: reconsiderar cómo enrutar
	@GetMapping("/")
	public String index(@CookieValue(value="_uuid", required=false) String cookie, Model model) {
		// Si no hay cookie, se devuelve la pantalla de inicio de sesión
		if (cookie != null) {
			model.addAttribute("loggedIn", true);
			User user = userRepo.findAll().get(0);
			model.addAttribute("user", user);
			return "profile";
		} else
			return "redirect/";
	}
	
	@GetMapping("/{id}")
	public String index(@PathVariable long id, @CookieValue(value="_uuid", required=false) String cookie, Model model) {
		// Si no hay cookie, se devuelve la pantalla de inicio de sesión
		
		if (cookie != null) model.addAttribute("loggedIn", true);
		User user = userRepo.getById(id);
		model.addAttribute("user", user);
		return "profile";
	}
}

package com.github.grandDAD2022.sheet.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

@Controller
public class ProfileController {
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/profile")
	public String index(Model model, HttpServletRequest request) {
		return "redirect:/@" + request.getUserPrincipal().getName();
	}

	@GetMapping("/@{username}")
	public String index(@PathVariable String username, Model model, HttpServletRequest request) {
		// TODO: comprobar cómo simplificar/generalizar este condicional
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ) {
			User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
			model.addAttribute("userId", user.getId());
			model.addAttribute("username", user.getUsername());
		}
		
		User profileUser = userRepo.findByUsername(username).get(0);
		model.addAttribute("profileUser", profileUser);
		model.addAttribute("posts", profileUser.getPosts());
		
		return "profile";
	}
}

package com.github.grandDAD2022.sheet.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

@Controller
public class MainController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) {
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		return "index";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signupForm() {
		return "signup";
	}
	@PostMapping("/signup")
	public String signup(User user, HttpServletResponse response) {
		// Registra el usuario
		userRepo.save(user);
		// y crea una cookie
		response.addCookie(new Cookie("_uuid", String.valueOf(user.getId())));
		return "redirect:/";
	}
}

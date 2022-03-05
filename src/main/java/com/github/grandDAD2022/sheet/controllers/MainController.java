package com.github.grandDAD2022.sheet.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import java.util.List;

@Controller
public class MainController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/")
	public String index(@CookieValue(value="_uuid", required=false) String cookie, Model model) {
		// Si no hay cookie, se devuelve la pantalla de inicio de sesión
		if (cookie != null) {
			User user = userRepo.findById(Long.parseLong(cookie)).get();
			model.addAttribute("userId", user.getId());
			model.addAttribute("username", user.getUsername());
			return "index";
		} else
			return "login";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	@PostMapping("/login")
	public String login(String username, String password, HttpServletResponse response) {
		// Busca el usuario
		List<User> query = userRepo.findByUsername(username);
		// Si existe, se añade una cookie
		// TODO: Implementar Spring Security y OAuth
		if (query.size() == 1)
				response.addCookie(new Cookie("_uuid", String.valueOf(query.get(0).getId())));
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		// Se crea una cookie nula que reemplaza a la original si existía
		Cookie cookie = new Cookie("_uuid", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
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

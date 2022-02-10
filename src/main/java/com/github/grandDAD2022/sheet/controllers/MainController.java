package com.github.grandDAD2022.sheet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login () {
		return "login_template";
	}
	
	@GetMapping("/user")
	public String userPage (Model model, @RequestParam String username, @RequestParam String password) {
		
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		
		return "userpage_template";
	}
}

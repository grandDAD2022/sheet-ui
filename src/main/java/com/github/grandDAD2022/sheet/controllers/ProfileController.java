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

@Controller
public class ProfileController {
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/profile")
	public String index(@CookieValue(value="_uuid", required=false) String cookie, Model model) {
		if (cookie != null) {
			User user = userRepo.findById(Long.parseLong(cookie)).get();
			return "redirect:/@" + user.getUsername();
		} else
			return "redirect:/";
	}

	@GetMapping("/@{username}")
	public String index(@PathVariable String username, @CookieValue(value="_uuid", required=false) String cookie, Model model) {
		if (cookie != null) {
			User user = userRepo.findById(Long.parseLong(cookie)).get();
			model.addAttribute("userId", user.getId());
			model.addAttribute("username", user.getUsername());
		}
		
		User profileUser = userRepo.findByUsername(username).get(0);
		model.addAttribute("profileUser", profileUser);
		model.addAttribute("posts", profileUser.getPosts());
		
		return "profile";
	}
}

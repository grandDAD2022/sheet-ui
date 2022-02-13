package com.github.grandDAD2022.sheet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.userRepository;

@Controller
public class MainController {
	
	@Autowired
	private userRepository userRep;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login () {
		return "login_template";
	}
	
	@GetMapping("/createaccount")
	public String createAccount (Model model, User user) {
		
		userRep.save(user);
		
		return "create_account_template";
	}
	
	@GetMapping("/user")
	public String userPage (Model model, User user) {
		model.addAttribute("username",user.getUsername());
		return "userpage_template";
	}
}

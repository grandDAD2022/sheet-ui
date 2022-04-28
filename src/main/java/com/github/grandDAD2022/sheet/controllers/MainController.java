package com.github.grandDAD2022.sheet.controllers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.atomfrede.jadenticon.Jadenticon;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

@Controller
public class MainController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ImageUploader image;
	
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
	public String signup(User user, HttpServletResponse response) throws IOException, TranscoderException {
		// Registra el usuario
		userRepo.save(user);
		image.upload(user, new FileSystemResource(Jadenticon.from(user.getUsername()).png()));
		// y crea una cookie
		response.addCookie(new Cookie("_uuid", String.valueOf(user.getId())));
		return "redirect:/";
	}
	@GetMapping("/posting")
	public String postingForm() {
		return "posting";
	}
	@PostMapping("/posting")
	public String posting(Post post, HttpServletRequest request) throws IOException, TranscoderException {
		// Busca el usuario
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		// Asignamos el usuario y guardamos el post en el repositorio
		post.setDate("28-04-2022");
		post.setUser(user);
		postRepo.save(post);
		return "redirect:/";
	}
}

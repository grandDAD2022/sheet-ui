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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.atomfrede.jadenticon.Jadenticon;
import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;
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
	private CommunityRepository commRepo;
	
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
		user.addPost(post);
		postRepo.save(post);
		return "redirect:/@" + user.getUsername();
	}
	@GetMapping("/create")
	public String createCommunityForm () {
		return "create";
	}
	@PostMapping("/create")
	public String createCommunity(Community c, HttpServletRequest request) throws IOException, TranscoderException {
		// Busca el usuario
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		// Asignamos el usuario y guardamos la comunidad en el repositorio
		user.createCommunity(c);
		commRepo.save(c);
		return "redirect:/community/@" + c.getId();
	}

	@GetMapping("/community/@{id}")
	public String community(@PathVariable long id, Model model) {
		Community c = commRepo.findById(id).get();
		model.addAttribute("profileCommunity", c);
		model.addAttribute("usersComm", c.getUser_in_community());
		model.addAttribute("admin", c.getAdmin_user().getUsername());
		model.addAttribute("posts", c.getPosts());
		
		return "community";
	}
	
	@GetMapping("/@{username}/communities")
	public String commList(@PathVariable String username, Model model, HttpServletRequest request) {
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("user", user);
		model.addAttribute("communities", user.getCommunities());
		
		return "communitiesList";
	}

}

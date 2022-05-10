package com.github.grandDAD2022.sheet.controllers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.atomfrede.jadenticon.Jadenticon;
import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

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
		image.uploadPfp(user, new FileSystemResource(Jadenticon.from(user.getUsername()).png()));
		// y crea una cookie
		response.addCookie(new Cookie("_uuid", String.valueOf(user.getId())));
		return "redirect:/";
	}
	@GetMapping("/posting")
	public String postingForm(Model model, HttpServletRequest request) {
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		return "posting";
	}
	@PostMapping("/posting")
	public String posting(Post post, @RequestParam MultipartFile pic, Model model, HttpServletRequest request) throws IOException, TranscoderException {
		// Busca el usuario
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		// Asignamos el usuario y guardamos el post en el repositorio
		user.addPost(post);
		postRepo.save(post);
		if (!pic.isEmpty()) image.uploadPostPic(post, pic.getResource());
		return "redirect:/@" + user.getUsername();
	}
	@GetMapping("/create")
	public String createCommunityForm (Model model, HttpServletRequest request) {
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		return "create";
	}

	@PostMapping("/create")
	public String createCommunity(Community c,Model model, HttpServletRequest request) throws IOException, TranscoderException {
		// Busca el usuario
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		// Asignamos el usuario y guardamos la comunidad en el repositorio
		user.createCommunity(c);
		commRepo.save(c);
		return "redirect:/community/@" + c.getId();
	}

	@GetMapping("/community/@{id}")
	public String community(@PathVariable long id, Model model, HttpServletRequest request) {
		Community c = commRepo.findById(id).get();
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("profileCommunity", c);
		model.addAttribute("usersComm", c.getUser_in_community());
		model.addAttribute("admin", c.getAdmin_user().getUsername());
		model.addAttribute("posts", c.getPosts());
		
		return "community";
	}

	@GetMapping("/@{username}/communities")
	public String commList(@PathVariable String username, Model model, HttpServletRequest request) {
		User user = userRepo.findByUsername(request.getUserPrincipal().getName()).get(0);
		model.addAttribute("userId", user.getId());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("user", user);
		model.addAttribute("communities", user.getCommunities());
		
		return "communitiesList";
	}
	
	@GetMapping("/img/{id}")
	public Mono<ResponseEntity<byte[]>> fetchImage(@PathVariable String id) {		
		WebClient client = WebClient.builder()
				.codecs(c ->
					c.defaultCodecs().maxInMemorySize(8 * 1024 * 1024))
				.baseUrl(System.getenv().getOrDefault("SHEET_MEDIA_URL", "http://localhost:42069"))
				.build();
		return client.get().uri("/" + id)
				.retrieve()
				.toEntity(byte[].class);
	}

}

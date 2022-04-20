package com.github.grandDAD2022.sheet.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.codec.multipart.FilePart;

import com.github.grandDAD2022.sheet.db.Comment;
import com.github.grandDAD2022.sheet.db.CommentRepository;
import com.github.grandDAD2022.sheet.db.Community;
import com.github.grandDAD2022.sheet.db.CommunityRepository;
import com.github.grandDAD2022.sheet.db.Notification;
import com.github.grandDAD2022.sheet.db.NotificationRepository;
import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nonapi.io.github.classgraph.utils.Assert;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "API de usuarios")
public class UserController {
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private PostRepository posts;
	
	@Autowired
	private CommentRepository comments;
	
	@Autowired
	private CommunityRepository communities;
	
	@Autowired
	private NotificationRepository notifications;
	
	@PostConstruct
	public void init() throws Exception {
		if (users.findAll().isEmpty()) {
			
		//Creamos los usuarios que estarán en la base de datos
			User s0 = new User("Rubén", "Vicente", "ruben@email.com", "09-02-2001", "699999999", "Hola!", "RubBen_19", "password");
			User s1 = new User("Pepe", "Martín", "pepe@mail.es", "04-12-1992", "612345789", "Hi!", "pepe92", "password");
		//Creamos los posts
			Post p0 = new Post("21-02-2021", "Primer post");
			Post p1 = new Post("19-02-2022", "Segundo post");
			Post p2 = new Post("22-02-2022", "Primer post de la comunidad");
		//Creamos comentarios	
			Comment c0 = new Comment("22-06-2023", "Primer comentario", null);
			Comment c1 = new Comment("26-02-2022", "Holaaa!", null);
			Comment c2 = new Comment("01-01-2023", "Feliz año!", null);
			Comment c3 = new Comment("22-02-2022", "Buen post", null);
		//Creamos una comunidad
			Community comm = new Community("22-03-2022", "Primera comunidad!");
		//Creo una notificacion
			Notification n1 = new Notification("", "18/04/2022", "Bienvenido a Sheet!!");
		//Añadimos la comunidad al usuario
			s1.createCommunity(comm);
		//Hacemos que el otro usuario se una a la comunidad
			s0.joinCommunity(comm);
		//Añadimos los posts a los usuarios
			s0.addPost(p0);
			s0.addPost(p2);
			s1.addPost(p1);
		//Añadimos los comentarios a los usuarios
			s0.createNewComment(c0);
			s0.createNewComment(c2);
			s1.createNewComment(c1);
			s1.createNewComment(c3);
		//Guardamos los usuarios en el repositorio
			users.save(s0);
			users.save(s1);
		//Añadimos los posts a la comunidad
			comm.addPost(p2);
		//Guardamos la comunidad
			communities.save(comm);
		//Añadimos los comentarios al post
			p0.addComment(c0);
			p0.addComment(c1);
			p1.addComment(c2);
			p2.addComment(c3);
		//Guardamos el post
			posts.save(p0);
			posts.save(p1);
			posts.save(p2);
		//Guardamos los comentarios
			comments.save(c0);
			comments.save(c1);
			comments.save(c2);
			comments.save(c3);
		//Adjuntamos las notificaciones 
			n1.newNotify(s1);
			n1.newNotify(s0);
		//Guardamos las notificaciones
			notifications.save(n1);
		}
	}
	
	@GetMapping("/")
	@Operation(summary = "Obtener lista de todos los usuarios")
	public Collection<User> getUsers() {
		return users.findAll();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtener lista de usuarios a partir de una id")
	public User getUser(@PathVariable long id) {
		return users.findById(id).orElseThrow();
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/{id}/pfp", produces = MediaType.IMAGE_PNG_VALUE)
	@Operation(summary = "Obtener foto de perfil de usuario")
	public @ResponseBody Flux<ResponseEntity> getProfileImage(@PathVariable long id) {
		users.findById(id).orElseThrow();
		WebClient client = WebClient.builder()
				  .baseUrl("http://localhost:42069")
				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				  .build();
		return client.get().uri("//" + users.getById(id).getImageId())
				.retrieve()
				.bodyToFlux(ResponseEntity.class);
	}
	
	@PutMapping(value = "/{id}/pfp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Actualizar foto de perfil de usuario")
	public void updateProfileImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
		User user = users.findById(id).orElseThrow();
		user.setProfileImage(imageFile.getBytes());
		users.save(user);
 	}
	
	@GetMapping("/{id}/usersnotifications")
	@Operation(summary = "Obtener lista de notificaciones de un usuario a partir de su id")
	public Collection<Notification> getNotifyUsers(@PathVariable long id) {
		users.findById(id).orElseThrow();
		return users.getById(id).getNotification();
	}
	
	@GetMapping("/{id}/users")
	@Operation(summary = "Obtener lista de usuarios de una comunidad a partir de su id")
	public Collection<User> getCommunityUsers(@PathVariable long id) {
		communities.findById(id).orElseThrow();
		return communities.getById(id).getUser_in_community();
	}
	
	@GetMapping("/{id}/admincommunities")
	@Operation(summary = "Obtener lista de las comunidades que pertenecen a un usuario a partir de su id")
	public Collection<Community> getAdminCommunities (@PathVariable long id) {
		users.findById(id).orElseThrow();
		return users.getById(id).getCom_admin();
	}
	
	@GetMapping("/{id}/comments")
	@Operation(summary = "Obtener la lista de comentarios de un usuario a partir de su id")
	public Collection<Comment> getUserComments (@PathVariable long id){
		users.findById(id).orElseThrow();
		return users.getById(id).getComments();
	}
	
	@GetMapping("/{id}/posts")
	@Operation(summary = "Obtener la lista de publicaciones de un usuario a partir de su id")
	public Collection<Post> getUserPosts (@PathVariable long id){
		users.findById(id).orElseThrow();
		return users.getById(id).getPosts();
	}
	
	@PostMapping("/")
	@Operation(summary = "Crear un usuario")
	public User createUser(@RequestBody User user) {
		users.save(user);
		return user;
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Destruir un usuario por su id")
	public User deleteUser(@PathVariable long id) {
		User user = users.findById(id).orElseThrow();
		for (int i = 0; i < user.getCommunities().size(); i++) {
			user.leaveCommunity(user.getCommunities().get(i));
		}
		users.deleteById(id);
		return null;
	}
	
	@DeleteMapping("/")
	@Operation(summary = "Destruir todos los usuarios")
	public void deleteUsers() {
		users.deleteAll();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar un usuario partiendo de su id")
	public User updateUser(@PathVariable long id, @RequestBody User newUser) {
		users.findById(id).orElseThrow();
		User u = users.getById(id);
		u.setFirstName(newUser.getFirstName());
		u.setSurname(newUser.getSurname());
		u.setE_mail(newUser.getE_mail());
		u.setDate_birth(newUser.getDate_birth());
		u.setTl_number(newUser.getTl_number());
		u.setBio(newUser.getBio());
		u.setUsername(newUser.getUsername());
		if (newUser.getPassword() != null)
			u.setPassword(newUser.getPassword());
		users.save(u);
		return newUser;
	}
 }

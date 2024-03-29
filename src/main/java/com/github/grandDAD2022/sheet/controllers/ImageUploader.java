package com.github.grandDAD2022.sheet.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.grandDAD2022.sheet.db.Post;
import com.github.grandDAD2022.sheet.db.PostRepository;
import com.github.grandDAD2022.sheet.db.User;
import com.github.grandDAD2022.sheet.db.UserRepository;

@Component
public class ImageUploader {
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private PostRepository posts;
	
	public void uploadPfp(User u, Resource r) {
		WebClient client = WebClient.builder()
				.codecs(c ->
					c.defaultCodecs().maxInMemorySize(8 * 1024 * 1024))
				.baseUrl(System.getenv().getOrDefault("SHEET_MEDIA_URL", "http://localhost:42069"))
				.build();
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("body", r)
                .header("Content-Type", "Multipart/related; type=\"image/png\"")
                .header("Content-Disposition", "form-data; name=mediaFile; filename=upload.png");
        Map<String,String> res = client.post().uri("/")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.body(BodyInserters.fromMultipartData(builder.build()))
			.retrieve()
		    .bodyToMono(new ParameterizedTypeReference<Map<String,String>>(){})
		    .block();
		u.setImageId(res.get("id"));
		users.save(u);
	}
	
	public void uploadPostPic(Post p, Resource r) {
		WebClient client = WebClient.builder()
				.codecs(c ->
					c.defaultCodecs().maxInMemorySize(8 * 1024 * 1024))
				.baseUrl(System.getenv().getOrDefault("SHEET_MEDIA_URL", "http://localhost:42069"))
				.build();
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("body", r)
                .header("Content-Type", "Multipart/related; type=\"image/png\"")
                .header("Content-Disposition", "form-data; name=mediaFile; filename=upload.png");
        Map<String,String> res = client.post().uri("/")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.body(BodyInserters.fromMultipartData(builder.build()))
			.retrieve()
		    .bodyToMono(new ParameterizedTypeReference<Map<String,String>>(){})
		    .block();
        p.setImage(res.get("id"));
        posts.save(p);
	}
}

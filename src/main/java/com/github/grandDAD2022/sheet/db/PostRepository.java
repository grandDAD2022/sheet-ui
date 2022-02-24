package com.github.grandDAD2022.sheet.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByUser (User user);
	List<Post> findByCommunity (Community community);
}
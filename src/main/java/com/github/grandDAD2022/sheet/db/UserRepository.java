package com.github.grandDAD2022.sheet.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	List<User> findByUsername(String username);
	List<User> findByFirstName (String firstname);
}
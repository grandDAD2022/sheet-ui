package com.github.grandDAD2022.sheet.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository  extends JpaRepository<Community,Long>{
	List<Community> findByName(String name);

	List<Community> findAll();
}

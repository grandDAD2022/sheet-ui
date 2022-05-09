package com.github.grandDAD2022.sheet.db;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames = "comunidades")
public interface CommunityRepository  extends JpaRepository<Community,Long>{
	
	@CacheEvict(allEntries=true)
	Community save(Community community);
	
	@Cacheable
	List<Community> findByName(String name);
	
	@Cacheable
	List<Community> findAll();
}

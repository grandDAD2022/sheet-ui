package com.github.grandDAD2022.sheet.db;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Counter {
	@Id public UUID id = UUID.randomUUID();
	@Size(min = 0) public Integer count = 0;
	
	public Counter() {}
}
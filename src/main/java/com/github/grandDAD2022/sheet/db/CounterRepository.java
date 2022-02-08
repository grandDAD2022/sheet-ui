package com.github.grandDAD2022.sheet.db;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, UUID> {}
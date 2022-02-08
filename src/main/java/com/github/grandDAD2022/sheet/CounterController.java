package com.github.grandDAD2022.sheet;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.grandDAD2022.sheet.db.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/count")
@Tag(name = "Count", description = "API para contar mediante contadores")
public class CounterController {
	@Autowired
	private CounterRepository repo;
	
	@GetMapping("/list")
	@Operation(summary = "Obtener lista de contadores")
	public Collection<Counter> getCounters() {
		return repo.findAll();
	}
	
	@PostMapping("/new")
	@Operation(summary = "Generar nuevo contador")
	public Counter newCounter() {
		return repo.save(new Counter());
	}
	
	@PutMapping("/add/{id}")
	@Operation(summary = "Incrementar contador")
	public Counter increaseCounter(
			@Parameter(description = "ID del contador")
			@PathVariable UUID id
	) {
		Counter counter = repo.findById(id).get();
		counter.count++;
		repo.save(counter);
		return counter;
	}
	
	@DeleteMapping("/del/{id}")
	@Operation(summary = "Destruir contador")
	public boolean deleteCounter(
			@Parameter(description = "ID del contador")
			@PathVariable UUID id
	) {
		repo.deleteById(id);
		return !repo.existsById(id);
	}
}
package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoJpaResource {
	

	@Autowired
	private TodoHardcodedService todoService;
	
	@Autowired
	private TodoJpaRepository todoJpaRepository;
	

	@GetMapping("/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {
		return todoJpaRepository.findByUsername(username);
		//return todoService.findAll();
	}

	@GetMapping("/jpa/users/{username}/todos/{id}")
	public Todo getTodos(@PathVariable String username, @PathVariable long id) {
		return todoJpaRepository.findById(id).get();
		//return todoService.findById(id);
	}

	// delete method
	@DeleteMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {

//		Todo t = todoService.deleteById(id);
//		if (t != null)
//			return ResponseEntity.noContent().build();
//		return ResponseEntity.notFound().build();
		
		todoJpaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// PUT request--updating a todo
	@PutMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id,
			@RequestBody Todo todo) {

		todo.setUsername(username);
		//Todo todoupdated = todoService.save(todo);
		Todo todoupdated = todoJpaRepository.save(todo);
		return new ResponseEntity<Todo>(todoupdated, HttpStatus.OK);
	}

	// PUT request--updating a todo
	@PostMapping("/jpa/users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username,
			@RequestBody Todo todo) {

		//Todo createdTodo = todoService.save(todo);
		
		todo.setUsername(username);
		
		Todo createdTodo = todoJpaRepository.save(todo);
		//add {id} url to the current url
		URI uri=ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
}

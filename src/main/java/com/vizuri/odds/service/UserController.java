package com.vizuri.odds.service;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vizuri.odds.entity.User;
import com.vizuri.odds.repository.UserRepository;



//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserRepository repository;
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public Iterable<?> findAll() {
		logger.info(">>>>>> Find All Users");
		return repository.findAll();
	}
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value="/{id}")
	public User findById(@PathVariable("id") Long id) {
		logger.info(">>>>>> Find User:"  + id);
		return repository.findOne(id);
	}
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public User create(@RequestBody User user) {
		logger.info(">>>>> Creating User:" + user.getId() + ":" + user.getLastName());
		return repository.save(user);
	}
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	  public User update(@PathVariable("id") String id, @RequestBody User user) {

	    logger.info(">>>>> Updating User:" + user.getId() + ":" + user.getLastName());
	    return repository.save(user);
	  }
	@RequestMapping(method = RequestMethod.DELETE, value="/{id}")
	public void delete(@PathVariable("id") Long id) {
		logger.info(">>>>> Deleting User:" + id);
		
		repository.delete(id);
	}
		
}

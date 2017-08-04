package com.vizuri.odds.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vizuri.odds.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findByLastName(String lastName);
}

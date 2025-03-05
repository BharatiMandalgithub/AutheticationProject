package com.example.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	
	//find the user by their user name 
	Optional<User> findByUsername(String username);

}

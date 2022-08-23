package com.study.springmvc.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springmvc.login.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	public Optional<User> findByName(String name);
	
	       Optional<User> findByPassword(String password);
}

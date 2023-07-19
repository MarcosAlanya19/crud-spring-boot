package com.example.forge.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.forge.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {}

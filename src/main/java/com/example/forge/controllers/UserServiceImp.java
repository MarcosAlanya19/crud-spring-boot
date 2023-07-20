package com.example.forge.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forge.models.entities.Role;
import com.example.forge.models.entities.User;
import com.example.forge.repositories.RoleRepository;
import com.example.forge.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {
  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return (List<User>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public User save(User user) {
    String passwordBCrypt =  passwordEncoder.encode(user.getPassword());
    user.setPassword(passwordBCrypt);
    Optional<Role> o = roleRepository.findByName("ROLE_USER");
    List<Role> roles = new ArrayList<>();
    if(o.isPresent()) {
      roles.add(o.orElseThrow());
    }
    user.setRoles(roles);
    return repository.save(user);
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public Optional<User> update(User user, Long id) {
    Optional<User> o = findById(id);
    User userOptional = null;
     if(o.isPresent()){
      User userDb = o.orElseThrow(null);
      userDb.setUsername(user.getUsername());
      userDb.setEmail(user.getEmail());
      userOptional = this.save(userDb);
    }
    return Optional.ofNullable(userOptional);
  }
}

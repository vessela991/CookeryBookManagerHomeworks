package fmi.springboot.vpopova.recipes.repository;

import fmi.springboot.vpopova.recipes.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
package de.dhbw.jwtdemo.users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Integer countByEmail(String email);

    Optional<User> findByEmail(String email);
}

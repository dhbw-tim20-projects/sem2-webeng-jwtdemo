package de.dhbw.jwtdemo.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    Integer countByEmail(String email);
}

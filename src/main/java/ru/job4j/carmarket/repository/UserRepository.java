package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserById(Integer id);

    User findByUsername(String username);
}

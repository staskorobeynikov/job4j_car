package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserById(Integer id);

    User findUserByAccount_LoginAndAccount_Password(String login, String password);
}

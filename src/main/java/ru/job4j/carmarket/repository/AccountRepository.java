package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.model.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findAccountByLogin(String login);
}

package ru.job4j.carmarket.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.carmarket.domain.Mark;

public interface MarkRepository extends CrudRepository<Mark, Integer> {

    Mark findMarkByMarkName(String name);
}

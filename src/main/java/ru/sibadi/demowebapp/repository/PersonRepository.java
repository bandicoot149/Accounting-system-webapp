package ru.sibadi.demowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibadi.demowebapp.domain.Person;

import java.util.ArrayList;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> persons = new ArrayList<>();

}

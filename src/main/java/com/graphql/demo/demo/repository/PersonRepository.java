package com.graphql.demo.demo.repository;

import com.graphql.demo.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, String> {

}

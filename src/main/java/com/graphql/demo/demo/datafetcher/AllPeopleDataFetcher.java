package com.graphql.demo.demo.datafetcher;

import com.graphql.demo.demo.model.Person;
import com.graphql.demo.demo.repository.PersonRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllPeopleDataFetcher implements DataFetcher<List<Person>> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> get(DataFetchingEnvironment env) {
        return personRepository.findAll();
    }
}

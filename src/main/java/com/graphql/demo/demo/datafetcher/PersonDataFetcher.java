package com.graphql.demo.demo.datafetcher;

import com.graphql.demo.demo.model.Person;
import com.graphql.demo.demo.repository.PersonRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PersonDataFetcher implements DataFetcher<Person>{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person get(DataFetchingEnvironment env) {
        Map arguments = env.getArguments();
        return personRepository.getOne((String) arguments.get("id"));
    }
}

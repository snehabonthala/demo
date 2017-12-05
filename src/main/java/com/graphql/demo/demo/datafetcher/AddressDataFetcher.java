package com.graphql.demo.demo.datafetcher;

import com.graphql.demo.demo.model.Address;
import com.graphql.demo.demo.model.Person;
import com.graphql.demo.demo.repository.AddressRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressDataFetcher implements DataFetcher<List<Address>>{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> get(DataFetchingEnvironment env) {

        Person person = env.getSource();
        return addressRepository.getAddressesByPerson(person.getId());

    }
}

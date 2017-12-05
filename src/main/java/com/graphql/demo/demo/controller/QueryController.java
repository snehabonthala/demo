package com.graphql.demo.demo.controller;

import com.graphql.demo.demo.datafetcher.AddressDataFetcher;
import com.graphql.demo.demo.datafetcher.AllPeopleDataFetcher;
import com.graphql.demo.demo.datafetcher.PersonDataFetcher;
import com.graphql.demo.demo.model.Address;
import com.graphql.demo.demo.model.Person;
import com.graphql.demo.demo.repository.AddressRepository;
import com.graphql.demo.demo.repository.PersonRepository;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
public class QueryController {

    private final Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Value(value="classpath:person.graphqls")
    private Resource schemaResource;

    private GraphQL graphQL;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AllPeopleDataFetcher allPeopleDataFetcher;

    @Autowired
    private PersonDataFetcher personDataFetcher;

    @Autowired
    private AddressDataFetcher addressDataFetcher;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = schemaResource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring  = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {

        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allPeople", allPeopleDataFetcher)
                        .dataFetcher("person", personDataFetcher))
                .type("Person", typeWiring-> typeWiring
                        .dataFetcher("addresses", addressDataFetcher))
                .build();
    }

    @RequestMapping(value="/query", method = RequestMethod.POST)
    public ResponseEntity query(@RequestBody String query){

        ExecutionResult result = graphQL.execute(query);
        logger.info(String.valueOf(result.getErrors()));
        return ResponseEntity.ok(result.getData());
    }

    @RequestMapping(value="/addresses", method= RequestMethod.GET)
    public @ResponseBody List<Address> getAll(){
        return addressRepository.getAddressesByPerson("1");
    }
}

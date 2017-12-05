package com.graphql.demo.demo.repository;

import com.graphql.demo.demo.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends CrudRepository<Address, String> {

    @Query("Select a from Address a where (a.person.id = :personId)")
    List<Address> getAddressesByPerson(@Param("personId")String personId);
}

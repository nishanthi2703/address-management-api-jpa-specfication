package com.example.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.example.address.model.Address;


/**
 * Repository is an interface that provides access to data in a database
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> , JpaSpecificationExecutor<Address> {
}

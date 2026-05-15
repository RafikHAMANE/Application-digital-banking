package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
	@Query("select c from Customer c where c.name like :kw")
	//avec cette ligne de Query, dans le controller il faut ajouter "%"..."%" mais par contre avec
	//FindByContain c'est pas la peine
	List<Customer> searchCustomer(@Param("kw") String keyword);

	Optional<Customer> findById(Long customerId);

}

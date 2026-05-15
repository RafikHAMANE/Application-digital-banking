package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
	List<BankAccount> findByCustomerId(Long customerId);

}

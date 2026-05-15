package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.AccountHistoryDto;
import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.BankAccountDto;
import com.example.demo.dtos.CurrentBankAccountDto;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.dtos.SavingBankAccountDto;
import com.example.demo.entities.BankAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.exception.BalanceNotSufficientException;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;

public interface BankAccountService {
	
	CustomerDto saveCustomer(CustomerDto customerDto);
	CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)throws CustomerNotFoundException;
	SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestrate, Long customerId)throws CustomerNotFoundException;
	List<CustomerDto> listeCustomers();
	BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException;
	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
	void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
	void transfer(String accountIdSource, String accountIdDistin,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
	List<BankAccountDto> bankAccountListe();
	CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException;
	CustomerDto updateCustomer(CustomerDto customerDto);
	void deletCustomer(Long customerId);
	List<AccountOperationDto> accountHistory(String accountId);
	AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
	List<CustomerDto> searchCustomers(String keyword);
	List<BankAccountDto> bankAccountListByCust(Long customerId) throws CustomerNotFoundException;
	

}

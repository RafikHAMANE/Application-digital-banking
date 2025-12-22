package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AccountHistoryDto;
import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.BankAccountDto;
import com.example.demo.dtos.CurrentBankAccountDto;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.dtos.SavingBankAccountDto;
import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.BankAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.enums.OperationType;
import com.example.demo.exception.BalanceNotSufficientException;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.mappers.BankAccountMapperImpl;
import com.example.demo.repositories.AccountOperationRepository;
import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional//utiliser @Transactional ?
//✔ Quand tu fais plusieurs opérations dépendantes :
//Exemples :
//transférer de l’argent entre deux comptes
//créer un user + créer son profil associé
//enregistrer une commande + enregistrer les articles
//modifier plusieurs entités en même temps
@AllArgsConstructor
@Slf4j //c'est une annotation lombok pour faire le log(logger)
public class BankAccountServiceImpl implements BankAccountService{
	
	private CustomerRepository customerRepository;
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private AccountOperationRepository accountOperationRepository;
	private BankAccountMapperImpl dtoMapper;

	@Override
	public CustomerDto saveCustomer(CustomerDto customerDto) {
		log.info("Saving New Customer");//pour logger
		Customer customer=dtoMapper.fromCustomerDto(customerDto);
		Customer savedCustomer=customerRepository.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}

	@Override
	public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
		Customer customer= customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
			
		CurrentAccount currentAccount=new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		//UUID.randomUUID():Cette partie génère un nouvel identifiant unique
		//(UUID = Universally Unique Identifier).
		//toString():Cette partie convertit l’UUID en texte (String).
		//Sans toString(), ce serait un objet de type UUID.
		//Avec toString(), ça devient une chaîne de caractères utilisable dans ta classe.

		currentAccount.setCreatedDat(new Date());
		currentAccount.setBalance(initialBalance);
		currentAccount.setOverDraft(overDraft);
		currentAccount.setCustomer(customer);
		CurrentAccount savedCurrentAcount= bankAccountRepository.save(currentAccount);
		return dtoMapper.fromCurrentBankAccount(savedCurrentAcount);
	}



	@Override
	public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestrate, Long customerId) throws CustomerNotFoundException {
		Customer customer= customerRepository.findById(customerId).orElse(null);
		if (customer==null)
			throw new CustomerNotFoundException("Customer not found");
		SavingAccount savingAccount=new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setCreatedDat(new Date());
		savingAccount.setBalance(initialBalance);
		savingAccount.setInterestRate(interestrate);
		savingAccount.setCustomer(customer);
		SavingAccount savedSavingAccount= bankAccountRepository.save(savingAccount);
		return dtoMapper.fromSavingBankAccount(savedSavingAccount); 
	}
	

	@Override
	public List<CustomerDto> listeCustomers() {
		
		List<Customer> customers= customerRepository.findAll();
		List<CustomerDto> customerDTOS=customers.stream()
				.map(customer -> dtoMapper.fromCustomer(customer))
				.collect(Collectors.toList());//ça remplace ces lignes qui sont en commentaire dessous
		/*
		List<CustomerDto> customerDTOS=new ArrayList<>();
		for(Customer customer:customers) {
			CustomerDto customerDTO=dtoMapper.fromCustomer(customer);
			customerDTOS.add(customerDTO);
		}
		*/ 
		//on fait ça pour récupérer les infos qui sont dans le DTO
		return customerDTOS;
		
	}

	@Override
	public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findById(accountId) 
				.orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
		if(bankAccount instanceof SavingAccount ) {
			SavingAccount savingAccount= (SavingAccount) bankAccount;
			return dtoMapper.fromSavingBankAccount(savingAccount);
		} else {
			CurrentAccount currentAccount= (CurrentAccount) bankAccount;
			return dtoMapper.fromCurrentBankAccount(currentAccount);
		}
		
	}

	@Override
	public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		BankAccount bankAccount = bankAccountRepository.findById(accountId) 
				.orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
		if (bankAccount.getBalance()<amount)
			throw new BalanceNotSufficientException("Balance not sufficient");
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.DEBIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
        accountOperation.setOperationDat(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
		
	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findById(accountId) 
				.orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.CREDIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
        accountOperation.setOperationDat(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
		
	}

	@Override
	public void transfer(String accountIdSource, String accountIdDistin, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		
		debit(accountIdSource,amount,description+"Transfer to"+accountIdDistin);
		credit(accountIdDistin,amount,description+"Transfer from"+accountIdSource);
		
		
	}
	
	@Override
	public List<BankAccountDto> bankAccountListe(){
		
		List<BankAccount> bankAccounts =bankAccountRepository.findAll();
		List<BankAccountDto> bankAccountDto= bankAccounts.stream().map(bankAccount ->{
			if (bankAccount instanceof SavingAccount) {
				SavingAccount savingAccount=(SavingAccount) bankAccount;
				return dtoMapper.fromSavingBankAccount(savingAccount);
			} else {
				CurrentAccount currentAccount=(CurrentAccount) bankAccount;
				return dtoMapper.fromCurrentBankAccount(currentAccount);
			}
		}).collect(Collectors.toList());
		return bankAccountDto;
	}
	
	@Override
	public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		return dtoMapper.fromCustomer(customer);
	}
	
	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto) {
		log.info("Updating Customer");//pour logger
		Customer customer=dtoMapper.fromCustomerDto(customerDto);
		Customer savedCustomer=customerRepository.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}
	
	@Override
	public void deletCustomer(Long customerId) {
		customerRepository.deleteById(customerId);
	}
	
	@Override
	public List<AccountOperationDto> accountHistory(String accountId){
		List<AccountOperation> accountOperations=accountOperationRepository.findByBankAccount_Id(accountId);
		return accountOperations.stream().map(op ->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
		
	}

	@Override
	public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException{
		// TODO Auto-generated method stub
		BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
		if (bankAccount==null) throw new BankAccountNotFoundException("BankAccount Not Found");
		Page<AccountOperation> accountOperations=accountOperationRepository.findByBankAccount_Id(accountId, PageRequest.of(page, size));
		AccountHistoryDto accountHistoryDto=new AccountHistoryDto();
		List<AccountOperationDto> accountOperationDtos=accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
		accountHistoryDto.setAccountOperationDto(accountOperationDtos);
		accountHistoryDto.setAccountId(bankAccount.getId());
		accountHistoryDto.setBalance(bankAccount.getBalance());
		accountHistoryDto.setCurrentPage(page);
		accountHistoryDto.setSizePage(size);
		accountHistoryDto.setTotalPages(accountOperations.getTotalPages());
		
		return accountHistoryDto;
	}
	



	

}

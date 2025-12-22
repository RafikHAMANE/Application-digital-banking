package com.example.demo.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.CurrentBankAccountDto;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.dtos.SavingBankAccountDto;
import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;

@Service
public class BankAccountMapperImpl {
	public CustomerDto fromCustomer(Customer customer) {
		CustomerDto customerDto=new CustomerDto();
		BeanUtils.copyProperties(customer,customerDto);//cette ligne remplace ce qui est dans ces 3lignes qui suit
		//CustomerDto.setId(customer.getId());
		//CustomerDto.setName(customer.getName());
		//CustomerDto.setEmail(customer.getEmail());
		return customerDto;
		
		/*
		 public CustomerDto fromCustomer(Customer customer)
CustomerDto = le type de retour (ce que la méthode renvoie).

fromCustomer = le nom de la méthode.

(Customer customer) = la liste des paramètres.

Pour ne jamais exposer directement les entités JPA sur Internet.

Pour renvoyer au Front uniquement ce dont il a besoin.

👉 Elle sert pour les méthodes GET : /customers, /customers/{id}, etc.
		 */
		
	}
	
	public Customer fromCustomerDto(CustomerDto customerDto) {
		Customer customer=new Customer();
		BeanUtils.copyProperties(customerDto,customer);
		
		return customer;
		
		/*
		 * Parce que Spring reçoit toujours du JSON → que tu transformes en DTO.

           Et toi tu dois convertir ce DTO en entité JPA pour pouvoir faire
		 */
		
	}
	
	public SavingBankAccountDto fromSavingBankAccount(SavingAccount savingAccount) {
		
		SavingBankAccountDto savingBankAccountDto=new SavingBankAccountDto();
		BeanUtils.copyProperties(savingAccount, savingBankAccountDto);
		savingBankAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
		savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
		return savingBankAccountDto;
		
	}
	
	public SavingAccount fromSavingBankAccountDto(SavingBankAccountDto savingBankAccountDto) {
		
		SavingAccount savingAccount=new SavingAccount();
		BeanUtils.copyProperties(savingBankAccountDto,savingAccount);
		savingAccount.setCustomer(fromCustomerDto(savingBankAccountDto.getCustomerDto()));
		return savingAccount;
		
	}
	
	public CurrentBankAccountDto fromCurrentBankAccount(CurrentAccount currentAccount) {
		CurrentBankAccountDto currentBankAccountDto=new CurrentBankAccountDto();
		BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
		currentBankAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
		currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
		return currentBankAccountDto;
		
	}
	
	public CurrentAccount fromCurrentBankAccountDto(CurrentBankAccountDto currentBankAccountDto) {
		CurrentAccount currentAccount=new CurrentAccount();
		BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
		currentAccount.setCustomer(fromCustomerDto(currentBankAccountDto.getCustomerDto()));
		return currentAccount;
		
	}
	
	public AccountOperationDto fromAccountOperation(AccountOperation accountOperation) {
		
		AccountOperationDto accountOperationDto=new AccountOperationDto();
		BeanUtils.copyProperties(accountOperation,accountOperationDto);
		return accountOperationDto;
		
	}

}

package com.example.demo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AccountHistoryDto;
import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.BankAccountDto;
import com.example.demo.dtos.CurrentBankAccountDto;
import com.example.demo.dtos.SavingBankAccountDto;
import com.example.demo.exception.BalanceNotSufficientException;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.service.BankAccountService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor

public class BankAccountRestController {
	 
	private BankAccountService bankAccountService;
	
	@GetMapping("/accounts/{accountId}")
	public BankAccountDto getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException{
		return bankAccountService.getBankAccount(accountId);
	}
	
	@GetMapping("/accounts")
	public List<BankAccountDto> ListAccounts(){
		return bankAccountService.bankAccountListe();
		
	}

	@PostMapping("/current")
    public CurrentBankAccountDto createCurrentAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @RequestParam Long customerId
    ) throws CustomerNotFoundException {

        return bankAccountService.saveCurrentBankAccount(
                initialBalance,
                overDraft,
                customerId
        );
    }
	
	@PostMapping("/saving")
    public SavingBankAccountDto createSavingAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @RequestParam Long customerId
    ) throws CustomerNotFoundException {

        return bankAccountService.saveSavingBankAccount(
                initialBalance,
                interestRate,
                customerId
        );
    }
	
			
			
	
	@GetMapping("/accounts/{accountId}/operations")
	public List<AccountOperationDto> getHistory(@PathVariable String accountId){
		return bankAccountService.accountHistory(accountId);
		
	}
	
	@GetMapping("/accounts/{accountId}/pageOperations")
	public AccountHistoryDto getAccountHistory(
			@PathVariable String accountId,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size) throws BankAccountNotFoundException {
		return bankAccountService.getAccountHistory(accountId,page,size);
		
	}
	
	@PostMapping("debit")
	public ResponseEntity<String> debit(
			@RequestParam String accountId,
			@RequestParam double amount,
			@RequestParam String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		bankAccountService.debit(accountId, amount,description);
		return ResponseEntity.ok("Débit effectué avec succès");
		//ResponseEntity:contrôle total de la réponse HTTP
	 
		
	}
	
	@PostMapping("credit")
	public ResponseEntity<String> credit(
			@RequestParam String accountId,
			@RequestParam double amount,
			@RequestParam String description) throws BankAccountNotFoundException {
		bankAccountService.credit(accountId, amount,description);
		return ResponseEntity.ok("Crédit effectué avec succès");
	
	
	
	}
	
	@PostMapping("transfer")
	public ResponseEntity<String> transfer(
			@RequestParam String accountIdSource,
			@RequestParam String accountIdDistin,
			@RequestParam double amount,
			@RequestParam String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
		bankAccountService.transfer(accountIdSource, accountIdDistin, amount, description);
		return ResponseEntity.ok("Transfert effectué avec succès");
		
	}

}

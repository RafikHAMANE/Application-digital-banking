package com.example.demo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AccountHistoryDto;
import com.example.demo.dtos.AccountOperationDto;
import com.example.demo.dtos.BankAccountDto;
import com.example.demo.dtos.CreditDto;
import com.example.demo.dtos.CurrentBankAccountDto;
import com.example.demo.dtos.DebitDto;
import com.example.demo.dtos.SavingBankAccountDto;
import com.example.demo.dtos.TransferRequestDto;
import com.example.demo.exception.BalanceNotSufficientException;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.service.BankAccountService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")//Autorise TOUTES les origines à accéder à ton backend.
//Angular,React,Postman,n’importe quel sit,peuvent appeler ton API.

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
	

	@PostMapping("/accounts/debit")
	public DebitDto debit(@RequestBody DebitDto debitDto) throws BankAccountNotFoundException, BalanceNotSufficientException {
		this.bankAccountService.debit(debitDto.getAccountId(), debitDto.getAmount(), debitDto.getDescription());
		return debitDto;
	}
	
	@PostMapping("/accounts/credit")
	public CreditDto credit(@RequestBody CreditDto creditDto) throws BankAccountNotFoundException {
		this.bankAccountService.credit(creditDto.getAccountId(), creditDto.getAmount(), creditDto.getDescription());
		return creditDto;
	}
	
	@PostMapping("/accounts/transfer")
	public void transfer(@RequestBody TransferRequestDto transferDto) throws BankAccountNotFoundException, BalanceNotSufficientException  {
		this.bankAccountService.transfer(transferDto.getAccountSource(), transferDto.getAccountDestination(), transferDto.getAmount(), transferDto.getDescription());
		
	}
	
	
	@GetMapping("/accounts/{customerId}/accounts")
	public List<BankAccountDto> getBankAccountByCust(@PathVariable Long customerId) throws CustomerNotFoundException{
		return bankAccountService.bankAccountListByCust(customerId);
		
	}

}

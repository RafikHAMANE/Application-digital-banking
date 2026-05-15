package com.example.demo.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.CustomerDto;
import com.example.demo.entities.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.service.BankAccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
	private BankAccountService bankAccountService;
	
	@GetMapping("/customers")
	public List<CustomerDto> customers(){
		return bankAccountService.listeCustomers();
	}
	
	@GetMapping("/customers/search")
	public List<CustomerDto> searchCustomers(@RequestParam(name="keyword",defaultValue="")String keyword){
		return bankAccountService.searchCustomers("%"+keyword+"%");//le signe des pourcentage
		//veut dire quelque soit le carractére avant et quelque soit le carractére aprés.
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDto getCustomer(@PathVariable(name="id")Long customerId) throws CustomerNotFoundException {
		return bankAccountService.getCustomer(customerId);
	}
	
	@PostMapping("/customers")
	public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
		return bankAccountService.saveCustomer(customerDto);
	}
	
	@PutMapping("/customers/{customerId}")
	public CustomerDto updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDto customerDto) {
		customerDto.setId(customerId);
		return bankAccountService.updateCustomer(customerDto);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		bankAccountService.deletCustomer(id);
	}
	

}

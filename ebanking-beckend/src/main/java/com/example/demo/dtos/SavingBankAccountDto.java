package com.example.demo.dtos;

import java.util.Date;

import com.example.demo.enums.AccountStatus;

import lombok.Data;

@Data
public class SavingBankAccountDto extends BankAccountDto {
	
	private String id;
	private Double balance;
	private Date createdDat;
	private AccountStatus Status;
	private CustomerDto customerDto;
	private double interestRate; 

}

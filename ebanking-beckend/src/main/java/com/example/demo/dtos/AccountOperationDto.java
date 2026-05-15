package com.example.demo.dtos;

import java.util.Date;

import com.example.demo.entities.BankAccount;
import com.example.demo.enums.OperationType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data

public class AccountOperationDto {
	
	private Long id;
	private Date operationDate;
	private double amount;
	private OperationType type;
	private String description;

}

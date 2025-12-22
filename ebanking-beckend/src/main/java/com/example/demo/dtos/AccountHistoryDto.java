package com.example.demo.dtos;

import java.util.List;

import lombok.Data;

@Data

public class AccountHistoryDto {
	
	private String accountId;
	private double Balance;
	private int currentPage;
	private int totalPages;
	private int SizePage;
	private List<AccountOperationDto> accountOperationDto;

}

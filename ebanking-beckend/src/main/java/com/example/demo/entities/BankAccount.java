package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.AccountStatus;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Inheritance(strategy=InheritanceType.JOINED)//pour faire la stratégie JOINED_TABLE, et aussi pour
//la classe mére on va la laisser abstract, et les classes filles on va les laisser tel qu'elles sont
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)//pour faire la stratégie TABLE_PER_CLASS
//et aussi on déclare la classe mére abstract pour qu'il ne la crée pas dans la base de données.
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)//pour faire single table
@DiscriminatorColumn(name="TYPE",length=4)//ici aussi pour faire single table(il va consédirer
//les classes filles comme des types et aussi il y a quoi faire dans les classes fille[DiscriminatorVlue])
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
	
	@Id
	private String id;
	private Double balance;
	private Date createdDat;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus Status;
	
	@ManyToOne
	private Customer customer;
	
	@OneToMany(mappedBy="bankAccount")
	private List <AccountOperation> accountOperations;
	
	private String description;

}

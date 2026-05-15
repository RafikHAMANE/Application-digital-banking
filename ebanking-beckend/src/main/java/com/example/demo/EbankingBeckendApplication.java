package com.example.demo;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dtos.BankAccountDto;
import com.example.demo.dtos.CurrentBankAccountDto;
import com.example.demo.dtos.CustomerDto;
import com.example.demo.dtos.SavingBankAccountDto;
import com.example.demo.entities.AccountOperation;
import com.example.demo.entities.BankAccount;
import com.example.demo.entities.CurrentAccount;
import com.example.demo.entities.Customer;
import com.example.demo.entities.SavingAccount;
import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.OperationType;
import com.example.demo.exception.BalanceNotSufficientException;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repositories.AccountOperationRepository;
import com.example.demo.repositories.BankAccountRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.service.BankAccountService;

@SpringBootApplication
public class EbankingBeckendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBeckendApplication.class, args);
	}
	@Bean//@Bean est une annotation Spring qui dit :

//👉 "La méthode qui suit va créer un objet que Spring doit gérer et mettre dans le conteneur."

//Cet objet devient un bean Spring, c’est-à-dire un composant que Spring peut :

//instancier

//configurer

//injecter dans d'autres classes

//garder en mémoire comme singleton (par défaut)
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
		return args-> {
			Stream.of("Hassan","Imane","Rafik").forEach(name->{
				CustomerDto customer=new CustomerDto();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listeCustomers().forEach(customer->{
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*90000,9000,customer.getId());
				}catch(CustomerNotFoundException e) {
					e.printStackTrace();
					}
			});
			List <BankAccountDto> bankAccounts=bankAccountService.bankAccountListe();
			for(BankAccountDto bankAccount:bankAccounts) {//est ce qu’on appelle une boucle for-each en Java.
				//Elle sert à parcourir toute une collection (par exemple une liste, un tableau, un Set…) pour traiter chaque élément un par un.
				//bankAccounts : c’est la collection (ex : List<BankAccount>) qui contient plusieurs comptes bancaires.
				//À chaque tour de boucle, la variable bankAccount va contenir un seul compte de cette liste.
				//Tu peux ensuite utiliser bankAccount pour appeler ses méthodes, lire ses attributs, etc
				try {
					for(int i=0;i<10;i++) {
						
						String accountId;
						if(bankAccount instanceof SavingBankAccountDto) {
							accountId=((SavingBankAccountDto)bankAccount).getId();
						}else {
							accountId=((CurrentBankAccountDto)bankAccount).getId();
						}
						bankAccountService.credit(accountId,10000+Math.random()*120000,"credit");
						bankAccountService.debit(accountId,1000+Math.random()*90000,"debit");
					}
				
					
				}catch(BankAccountNotFoundException | BalanceNotSufficientException e) {
					e.printStackTrace();
				}
			}// <-- ici on ferme la boucle for(BankAccount ...)
		}; // <-- ici on ferme le lambda args -> { ... }
	}// <-- ici on ferme la méthode commandLineRunner
				
//on utilise la boucle for(i),quand:Tu as besoin de l’index (i)
	                              //Tu veux parcourir une partie d’une liste
	                              //Tu veux modifier la liste pendant le parcours
//on utlise aussi la boucle for-each quand:Tu veux juste parcourir tous les éléments, sans te soucier des index.
	         //Tu veux rendre ton code plus simple et plus lisible (For-each est plus court et propre)
	         //Tu n’as pas besoin de l’index (C’est idéal pour parcourir )
//Ce que for-each NE PEUT PAS faire :
	        //1️⃣ Sauter des éléments, faire i = i + 2
			//2️⃣ Accéder facilement au suivant, précédent (i+1, i-1)
			//3️⃣ Modifier les éléments d'une liste avec leur position (set(i, value))
			//4️⃣ Supprimer un élément pendant le parcours (provoque une ConcurrentModificationException)
//Dans ces cas → tu utilises for(i) ou un Iterator.	
	

	


//CommandLineRunner est une interface de Spring Boot qui te permet d’exécuter du code 
	//automatiquement au démarrage de l’application.
			
	//Quand utilise-t-on un CommandLineRunner :
	              //1. Initialiser la base de données
	              //2. Tester rapidement des méthodes de services
	              //3. Exécuter une logique technique au démarrage
	              //4. Effectuer des démonstrations
	
	
//on utilise la classe lambda args -> { ... }quand:
	//A. Quand une interface possède UNE seule méthode (interface fonctionnelle)par exemple
	//comandeLineRunner
	//B. Quand tu veux remplacer une classe anonyme
	//C. Quand tu veux écrire du code d’exécution
	
					
						
						
						
							
						
						
					
					
				
					
				
					
				
					
				
		
		
		
	
	
	
	//@Bean
		CommandLineRunner start(CustomerRepository customerRepository, 
				                BankAccountRepository bankAccountRepository,
				                AccountOperationRepository accountOperationRepository
				                
				          ) {
			return args -> {
				Stream.of("Hassan","Yassine","Aicha").forEach(name->{
					Customer customer=new Customer();
					customer.setName(name);
					customer.setEmail(name+"@gmail.com");
					customerRepository.save(customer);
					
				});
			    customerRepository.findAll().forEach(cust->{
			    	
				    CurrentAccount currentAccount=new CurrentAccount();
				    currentAccount.setId(UUID.randomUUID().toString());
				    currentAccount.setBalance(Math.random()*90000);
				    currentAccount.setCreatedDat(new Date());
				    currentAccount.setStatus(AccountStatus.CREATED);
				    currentAccount.setCustomer(cust);
				    currentAccount.setOverDraft(9000);
				    bankAccountRepository.save(currentAccount);
				    
				    SavingAccount savingAccount=new SavingAccount();
				    savingAccount.setId(UUID.randomUUID().toString());
				    savingAccount.setBalance(Math.random()*90000);
				    savingAccount.setCreatedDat(new Date());
				    savingAccount.setStatus(AccountStatus.CREATED);
				    savingAccount.setCustomer(cust);
				    savingAccount.setInterestRate(5);
				    bankAccountRepository.save(savingAccount);
				   
				 
				
			    });
			    bankAccountRepository.findAll().forEach(acc->{
			    	for(int i=0; i<10;i++) {
			    		AccountOperation accountOperation=new AccountOperation();
			    		accountOperation.setOperationDate(new Date());
			    		accountOperation.setAmount(Math.random()*12000);
			    		accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
			    		accountOperation.setBankAccount(acc);
			    		accountOperationRepository.save(accountOperation);
			    	}
			    });
			    
			    
			    
			    
				
				
				
			};
		}
}

package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity//est une des annotations les plus importantes de JPA / Hibernate dans Spring Boot
       //Elle sert à dire à Spring Boot:"Cette classe représente une table dans la base de données."
@Data //Génére automatiquement les getters + setters + toString + equals + hashCode + constructeur minimal
@NoArgsConstructor //Génère un constructeur vide
@AllArgsConstructor //Génère un constructeur avec tous les champs
public class Customer {
	
    @Id//Cette annotation indique la clé primaire de ton entité.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Cette 
    //annotation dit comment la clé primaire doit être générée
    //GenerationType.IDENTITY signifie que c’est la base de données qui génère
    //automatiquement la valeur du champ (souvent un AUTO_INCREMENT).
	private Long id;
	private String name;
	private String email;
	@OneToMany(mappedBy="customer")//(mappedBy="customer"): c'est dire à JPA que il y a un attribut 
	                               //qui s'appelle customer dans la classe BankAccount qui utilise 
	                               //l'anotation ManyToOne et qu'il s'agit de le méme relation que
	                               //celle la pour qu'il ceiz une seule clé étrangére, si non il 
	                               //va tenter de crier deux clés étrangéres(il les consédire comme
	                               //            2 relations déffirentes).
	private List<BankAccount> bankAccounts;
	

}

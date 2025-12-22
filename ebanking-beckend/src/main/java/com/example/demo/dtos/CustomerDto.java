package com.example.demo.dtos;

import lombok.Data;

@Data
public class CustomerDto {
	private Long id;
	private String name;
	private String email;

}
//ici dans ce CustomerDto je vais récupérer seulement les attributs que je vais afficher dans 
//le fichier json de mon entitée customer, et j'élimine toutes les annotation comme @Entity,
//@NoArgsConstructor, @AllArgsConstructors et aussi ces @Id, @GeneratedValue et @OneToMany
//(la relation biridectionnel )avec meme l'attribut.
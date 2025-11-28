package com.firma.gestiune_it.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Spune Spring-ului ca asta e o tabela in baza de date
@Data // Lombok: genereaza automat Getteri, Setteri, toString, hashCode
@NoArgsConstructor // Lombok: genereaza constructorul gol (obligatoriu pentru JPA)
@Table(name = "utilizatori") // Specificam numele tabelului in MySQL
public class Utilizator {

    @Id // Cheia primara
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment (1, 2, 3...)
    @Column(name = "id_utilizator")
    private Long id;

    @Column(nullable = false)
    private String nume; // Numele real (ex: Ion Ionescu)

    @Column(unique = true, nullable = false)
    private String username; // Numele de utilizator pentru login

    @Column(nullable = false)
    private String parola; // Parola criptata

    @Column(nullable = false)
    private String rol; // ROLE_USER sau ROLE_EDITOR
}
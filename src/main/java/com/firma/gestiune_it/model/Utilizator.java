package com.firma.gestiune_it.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // spune Spring-ului ca asta e o tabela in baza de date
@Data // Lombok: genereaza automat Getteri, Setteri, toString, hashCode
@NoArgsConstructor // Lombok: genereaza constructorul gol (obligatoriu pentru JPA)
@Table(name = "utilizatori") // Specificam numele tabelului in MySQL
public class Utilizator {

    @Id // Cheia primara
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto inceremet
    @Column(name = "id_utilizator")
    private Long id;

    @Column(nullable = false)
    private String nume;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String parola; // parola criptata

    @Column(nullable = false)
    private String rol;
}
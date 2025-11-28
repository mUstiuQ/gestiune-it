package com.firma.gestiune_it.repository;

import com.firma.gestiune_it.model.Echipament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // Ne ajuta la filtrare dinamica

// Atentie: Cheia primara aici e String (numarInventar), nu Long
public interface EchipamentRepository extends JpaRepository<Echipament, String>, JpaSpecificationExecutor<Echipament> {
    // Aici vom adauga metode de filtrare mai tarziu
}
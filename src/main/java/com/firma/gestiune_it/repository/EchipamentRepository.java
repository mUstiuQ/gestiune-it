package com.firma.gestiune_it.repository;

import com.firma.gestiune_it.model.Echipament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // ne ajuta la filtrare dinamica

//cheia primara aici e String (numarInventar)g
public interface EchipamentRepository extends JpaRepository<Echipament, String>, JpaSpecificationExecutor<Echipament> {
    // putem sa mai adaugam metode de filtrare
}
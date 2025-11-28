package com.firma.gestiune_it.repository;

import com.firma.gestiune_it.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Extindem JpaRepository <Clasa, TipulCheiiPrimare>
public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {

    // Spring stie automat sa faca SQL-ul din spatele acestei metode doar citind numele ei!
    // "SELECT * FROM utilizatori WHERE username = ?"
    Optional<Utilizator> findByUsername(String username);
}
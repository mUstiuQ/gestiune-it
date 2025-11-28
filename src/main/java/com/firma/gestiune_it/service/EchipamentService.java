package com.firma.gestiune_it.service;

import com.firma.gestiune_it.model.Echipament;
import com.firma.gestiune_it.repository.EchipamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spune Spring-ului ca aceasta clasa contine logica de business
public class EchipamentService {

    @Autowired
    private EchipamentRepository echipamentRepository;

    // Metoda care returneaza lista cu TOATE echipamentele
    public List<Echipament> getAllEchipamente() {
        return echipamentRepository.findAll();
    }

    // Vom adauga aici mai tarziu metoda de filtrare
}
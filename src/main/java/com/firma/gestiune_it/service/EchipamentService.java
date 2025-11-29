package com.firma.gestiune_it.service;

import com.firma.gestiune_it.model.Echipament;
import com.firma.gestiune_it.repository.EchipamentRepository;
import jakarta.persistence.criteria.Predicate; // Atentie la import!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification; // Atentie la import!
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class EchipamentService {

    @Autowired
    private EchipamentRepository echipamentRepository;

    public List<Echipament> getAllEchipamente() {
        return echipamentRepository.findAll();
    }

    // Aceasta este metoda magica pentru Filtrare Dinamica
    public List<Echipament> filtreazaEchipamente(String brand, String tip, String os) {

        Specification<Echipament> spec = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();

            // 1. daca utilizatorul a scris ceva la Brand, adaugam regula
            if (StringUtils.hasText(brand)) {
                // like %text% inseamna ca gaseste si daca scrii doar "De" pentru "Dell"
                predicateList.add(cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%"));
            }

            // 2. daca a scris ceva la Tip
            if (StringUtils.hasText(tip)) {
                predicateList.add(cb.like(cb.lower(root.get("tipEchipament")), "%" + tip.toLowerCase() + "%"));
            }

            // 3. daca a scris ceva la OS
            if (StringUtils.hasText(os)) {
                predicateList.add(cb.like(cb.lower(root.get("sistemOperare")), "%" + os.toLowerCase() + "%"));
            }

            // 4. le unim pe toate cu AND
            return cb.and(predicateList.toArray(new Predicate[0]));
        };

        return echipamentRepository.findAll(spec);
    }
}
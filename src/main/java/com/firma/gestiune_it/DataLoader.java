package com.firma.gestiune_it;

import com.firma.gestiune_it.model.Echipament;
import com.firma.gestiune_it.model.Utilizator;
import com.firma.gestiune_it.repository.EchipamentRepository;
import com.firma.gestiune_it.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private UtilizatorRepository utilizatorRepository;
    @Autowired private EchipamentRepository echipamentRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. verificam si cream utilizatorii
        if (utilizatorRepository.count() == 0) {
            Utilizator user = new Utilizator();
            user.setNume("David Andrei"); user.setUsername("angajat"); user.setParola(passwordEncoder.encode("david123")); user.setRol("ROLE_ANGAJAT_MEDIOCRU");
            utilizatorRepository.save(user);

            Utilizator editor = new Utilizator();
            editor.setNume("Fodoka David-Andrei"); editor.setUsername("admin"); editor.setParola(passwordEncoder.encode("editor321!")); editor.setRol("ROLE_ADMIN_REGAL");
            utilizatorRepository.save(editor);
        }

        // 2. adaugam echipamente doar daca tabelul e gol
        if (echipamentRepository.count() == 0) {
            // trebuie sa stim cine a adaugat echipamentul
            Utilizator editor = utilizatorRepository.findByUsername("admin").get();

            Echipament e1 = new Echipament();
            e1.setNumarInventar("IT-001");
            e1.setBrand("Dell");
            e1.setModel("Latitude 5420");
            e1.setStatus("Activ");
            e1.setTipEchipament("Laptop");
            e1.setAnAchizitie(2022);
            e1.setProcesor("i5-1135G7");
            e1.setMemorieRam(16);
            e1.setCapacitateStocare(512);
            e1.setSistemOperare("Windows 10 Pro");
            e1.setPret(4500.0);
            e1.setUtilizator(editor); // legatura cu adminul
            echipamentRepository.save(e1);

            Echipament e2 = new Echipament();
            e2.setNumarInventar("IT-002");
            e2.setBrand("HP");
            e2.setModel("ProBook 450");
            e2.setStatus("In stoc");
            e2.setTipEchipament("Laptop");
            e2.setAnAchizitie(2021);
            e2.setProcesor("i7-10510U");
            e2.setMemorieRam(8);
            e2.setCapacitateStocare(256);
            e2.setSistemOperare("Linux Ubuntu");
            e2.setPret(3200.0);
            e2.setUtilizator(editor);
            echipamentRepository.save(e2);

            System.out.println("Echipamentele de test au fost create!");
        }
    }
}
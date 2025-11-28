package com.firma.gestiune_it;

import com.firma.gestiune_it.model.Utilizator;
import com.firma.gestiune_it.repository.UtilizatorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional // Foarte important: Sterge datele de test dupa ce termina!
class GestiuneItApplicationTests {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Test
    void testSalvareSiGasireUtilizator() {
        System.out.println("--- INCEPE TESTUL UNITAR ---");

        // 1. Cream un utilizator fictiv
        Utilizator userTest = new Utilizator();
        userTest.setNume("Tester Automat");
        userTest.setUsername("test_user");
        userTest.setParola("parola_test");
        userTest.setRol("ROLE_USER");

        // 2. Il salvam in baza de date
        utilizatorRepository.save(userTest);

        // 3. Verificam daca a primit un ID (semn ca a fost salvat in MySQL)
        Assertions.assertNotNull(userTest.getId(), "Utilizatorul nu a primit ID, salvarea a esuat!");

        // 4. Incercam sa il gasim dupa username
        Optional<Utilizator> gasit = utilizatorRepository.findByUsername("test_user");

        // 5. Verificam daca l-am gasit si daca numele e corect
        Assertions.assertTrue(gasit.isPresent(), "Utilizatorul nu a fost gasit in baza de date!");
        Assertions.assertEquals("Tester Automat", gasit.get().getNume());

        System.out.println("--- TEST REUSIT: Utilizatorul a fost salvat si verificat! ---");
    }

}
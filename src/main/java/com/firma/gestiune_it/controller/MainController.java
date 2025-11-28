package com.firma.gestiune_it.controller;

import com.firma.gestiune_it.model.Echipament; // Import nou
import com.firma.gestiune_it.model.Utilizator;
import com.firma.gestiune_it.repository.UtilizatorRepository;
import com.firma.gestiune_it.service.EchipamentService; // Import nou
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired private UtilizatorRepository utilizatorRepository;
    @Autowired private EchipamentService echipamentService; // 1. Injectam Service-ul

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        String username = principal.getName();
        Utilizator userLogat = utilizatorRepository.findByUsername(username).get();

        model.addAttribute("mesajBunVenit", "Bine ai venit, " + userLogat.getNume());
        model.addAttribute("rolUtilizator", userLogat.getRol());

        // 2. Cerem lista de echipamente de la Service
        List<Echipament> listaMea = echipamentService.getAllEchipamente();

        // 3. O punem in "coletul" (Model) catre HTML sub numele "echipamente"
        model.addAttribute("echipamente", listaMea);

        return "index";
    }

    @GetMapping("/login")
    public String login() { return "login"; }
}
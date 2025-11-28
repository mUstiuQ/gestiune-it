package com.firma.gestiune_it.controller;

import com.firma.gestiune_it.model.Echipament; // Import nou
import com.firma.gestiune_it.model.Utilizator;
import com.firma.gestiune_it.repository.EchipamentRepository;
import com.firma.gestiune_it.repository.UtilizatorRepository;
import com.firma.gestiune_it.service.EchipamentService; // Import nou
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired private UtilizatorRepository utilizatorRepository;
    @Autowired private EchipamentService echipamentService; // 1. Injectam Service-ul
    @Autowired private EchipamentRepository echipamentRepository; // Injectam si repo-ul direct pentru stergere rapida

    @GetMapping("/")
    public String index(Model model, Principal principal,
                        // Acesti parametri sunt optionali (required = false)
                        @RequestParam(required = false) String brand,
                        @RequestParam(required = false) String tip,
                        @RequestParam(required = false) String os) {

        String username = principal.getName();
        Utilizator userLogat = utilizatorRepository.findByUsername(username).get();

        model.addAttribute("mesajBunVenit", "Bine ai venit, " + userLogat.getNume());
        model.addAttribute("rolUtilizator", userLogat.getRol());

        List<Echipament> listaFiltrata;
        String mesajTabel;

        // Verificam daca s-a facut vreo filtrare
        boolean areFiltre = (brand != null && !brand.isEmpty()) ||
                (tip != null && !tip.isEmpty()) ||
                (os != null && !os.isEmpty());

        if (areFiltre) {
            listaFiltrata = echipamentService.filtreazaEchipamente(brand, tip, os);
            mesajTabel = "Rezultate filtrare pentru: " +
                    (brand != null && !brand.isEmpty() ? " Brand='" + brand + "'" : "") +
                    (tip != null && !tip.isEmpty() ? " Tip='" + tip + "'" : "") +
                    (os != null && !os.isEmpty() ? " OS='" + os + "'" : "");
        } else {
            listaFiltrata = echipamentService.getAllEchipamente();
            mesajTabel = "Toate echipamentele din parc";
        }

        model.addAttribute("echipamente", listaFiltrata);
        model.addAttribute("mesajTabel", mesajTabel);

        // Trimitem valorile inapoi in pagina ca sa ramana scrise in casute
        model.addAttribute("filtruBrand", brand);
        model.addAttribute("filtruTip", tip);
        model.addAttribute("filtruOs", os);

        return "index";
    }

    @GetMapping("/stergere/{id}")
    public String stergeEchipament(@PathVariable String id) {
        // Stergem echipamentul pe baza ID-ului (numarInventar)
        echipamentRepository.deleteById(id);

        // Ne intoarcem la pagina principala
        return "redirect:/";
    }

    @GetMapping("/adaugare")
    public String arataFormularAdaugare(Model model) {
        // Trimitem un obiect gol catre HTML
        model.addAttribute("echipamentForm", new Echipament());
        model.addAttribute("titluPagina", "Adaugă Echipament Nou");
        model.addAttribute("modEditare", false);
        return "formular";
    }

    // 2. Afisare Formular Plin (Editare)
    @GetMapping("/editare/{id}")
    public String arataFormularEditare(@PathVariable String id, Model model) {
        // Cautam echipamentul existent
        Echipament echipamentExistent = echipamentRepository.findById(id).get();

        model.addAttribute("echipamentForm", echipamentExistent);
        model.addAttribute("titluPagina", "Editează Echipamentul " + id);
        model.addAttribute("modEditare", true);
        return "formular";
    }

    // 3. Salvarea Datelor (Valabil si pt Adaugare si pt Editare)
    @PostMapping("/salvare")
    public String salveazaEchipament(@ModelAttribute("echipamentForm") Echipament echipament, Principal principal) {
        // Trebuie sa setam Utilizatorul care face modificarea (Editorul curent)
        String username = principal.getName();
        Utilizator editor = utilizatorRepository.findByUsername(username).get();

        echipament.setUtilizator(editor);

        // Functia save face "Upsert":
        // Daca ID-ul exista deja -> face UPDATE.
        // Daca ID-ul e nou -> face INSERT.
        echipamentRepository.save(echipament);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() { return "login"; }
}
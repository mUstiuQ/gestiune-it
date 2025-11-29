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
    @Autowired private EchipamentService echipamentService; // injectam Service-ul
    @Autowired private EchipamentRepository echipamentRepository; // injectam si repo-ul direct pentru stergere rapida

    @GetMapping("/")
    public String index(Model model, Principal principal,
                        // parametrii optionali pt testing (required = false)
                        @RequestParam(required = false) String brand,
                        @RequestParam(required = false) String tip,
                        @RequestParam(required = false) String os) {

        String username = principal.getName();
        Utilizator userLogat = utilizatorRepository.findByUsername(username).get();

        model.addAttribute("mesajBunVenit", "Bine ai venit, " + userLogat.getNume());
        model.addAttribute("rolUtilizator", userLogat.getRol());

        List<Echipament> listaFiltrata;
        String mesajTabel;

        // verificam daca s a facut vreo filtrare
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
        }

        model.addAttribute("echipamente", listaFiltrata);


        // trimitem valorile inapoi in pagina ca sa ramana scrise in casute
        model.addAttribute("filtruBrand", brand);
        model.addAttribute("filtruTip", tip);
        model.addAttribute("filtruOs", os);

        return "index";
    }

    @GetMapping("/stergere/{id}")
    public String stergeEchipament(@PathVariable String id) {
        // stergem echipamentul pe baza ID-ului
        echipamentRepository.deleteById(id);

        // ne intoarcem la pagina principala
        return "redirect:/";
    }

    @GetMapping("/adaugare")
    public String arataFormularAdaugare(Model model) {
        // trimitem un obiect gol catre HTML
        model.addAttribute("echipamentForm", new Echipament());
        model.addAttribute("titluPagina", "Adaugă Echipament Nou");
        model.addAttribute("modEditare", false);
        return "formular";
    }

    //  afisare formular plin (pt editare)
    @GetMapping("/editare/{id}")
    public String arataFormularEditare(@PathVariable String id, Model model) {
        // Cautam echipamentul existent
        Echipament echipamentExistent = echipamentRepository.findById(id).get();

        model.addAttribute("echipamentForm", echipamentExistent);
        model.addAttribute("titluPagina", "Editează Echipamentul " + id);
        model.addAttribute("modEditare", true);
        return "formular";
    }

    //salvarea datelor (atat pt adaugare cat si pt editare)
    @PostMapping("/salvare")
    public String salveazaEchipament(@ModelAttribute("echipamentForm") Echipament echipament, Principal principal) {
        // trebuie sa setam utilizatorul care face modificarea
        String username = principal.getName();
        Utilizator editor = utilizatorRepository.findByUsername(username).get();

        echipament.setUtilizator(editor);

        // functia save face "Upsert":
        // daca ID-ul exista deja -> face UPDATE.
        // daca ID-ul e nou -> face INSERT.
        echipamentRepository.save(echipament);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() { return "login"; }
}
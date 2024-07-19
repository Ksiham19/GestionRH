package com.example.gestionrh.URLControllers;

import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Services.CandidatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/candidatUrl")
public class URLCandidat {

    private final CandidatService serviceCandidat;

    public URLCandidat(CandidatService serviceCandidat) {
        this.serviceCandidat = serviceCandidat;
    }

    @GetMapping
    public String showLoginForm(Model model) {
        return "login.html";
    }

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute("candidat") Candidat candidat, Model model) {
        serviceCandidat.createCandidat(candidat);
        model.addAttribute("message", "Un nouveau candidat ajouté");
        return "page1/login";
    }

}

package com.example.gestionrh.URLControllers;

import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Entities.Candidature;
import com.example.gestionrh.Entities.OffreEmploi;
import com.example.gestionrh.Entities.ResponsableRH;
import com.example.gestionrh.Services.CandidatService;
import com.example.gestionrh.Services.CandidatureService;
import com.example.gestionrh.Services.OffreEmploiService;
import com.example.gestionrh.Services.ResponsableRHService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/candidat")
public class mappingCandiat {
    CandidatService candidatService;
    CandidatureService candidatureService;
    OffreEmploiService offreEmploiService;
    ResponsableRHService responsableRHService;

    public mappingCandiat(ResponsableRHService responsableRHService,CandidatService candidatService,OffreEmploiService offreEmploiService,CandidatureService candidatureService){
        this.candidatService=candidatService;
        this.offreEmploiService=offreEmploiService;
        this.candidatureService=candidatureService;
        this.responsableRHService=responsableRHService;
    }

    @GetMapping("/OffreEmploie")
    public String OffreEmploie(@RequestParam(name = "id", required = false) Long candidatId, Model model) {
        if (candidatId != null) {
            Optional<Candidat> candidat = candidatService.getCandidatById(candidatId);
            model.addAttribute("candidat", candidat.orElse(null));
        }
        return "accriiloffre";
    }

    @GetMapping("/MesCandidatures")
    public String MesCandidatures(@RequestParam(name = "id", required = false) Long candidatId, Model model) {
        if (candidatId != null) {
            Candidat candidat = candidatService.getCandidatById(candidatId).orElse(null);
            if (candidat != null) {
                List<Candidature> candidatures = candidat.getCandidatures();
                model.addAttribute("candidatures", candidatures);
                model.addAttribute("candidat",candidat);
            } else {
                model.addAttribute("error", "Candidat non trouvé");
            }
        }
        return "MesCondidatuespourCandidat";
    }

    @GetMapping("/cuisine")
    public String cuisine(@RequestParam(name = "id", required = false) Long candidatId, Model model) {
        List<OffreEmploi> resultats = offreEmploiService.getOffresByType("cuisine");

        Optional<Candidat> candidat = candidatService.getCandidatById(candidatId);
        // Optional: Use the candidatId if needed
        if (candidat != null) {
            model.addAttribute("candidat", candidat);
        }

        model.addAttribute("resultats", resultats);
        return "OffrespourCandidat";
    }

    @GetMapping("/securite")
    public String securite(@RequestParam(name = "id", required = false) Long candidatId, Model model) {
        List<OffreEmploi> resultats = offreEmploiService.getOffresByType("securite");
        // Optional: Use the candidatId if needed
        if (candidatId != null) {
            model.addAttribute("candidatId", candidatId);
        }

        model.addAttribute("resultats", resultats);
        return "OffrespourCandidat";
    }

    @GetMapping("/staff")
    public String staff(@RequestParam(name = "id", required = false) Long candidatId, Model model) {
        List<OffreEmploi> resultats = offreEmploiService.getOffresByType("staff");
        // Optional: Use the candidatId if needed
        Optional<Candidat> candidat = candidatService.getCandidatById(candidatId);
        // Optional: Use the candidatId if needed
        if (candidat != null) {
            model.addAttribute("candidat", candidat);
        }

        model.addAttribute("resultats", resultats);
        return "OffrespourCandidat";
    }

    @GetMapping("/docteur")
    public String docteur(@RequestParam(name = "id", required = false) Long candidatId, Model model) {
        List<OffreEmploi> resultats = offreEmploiService.getOffresByType("docteur");
        Optional<Candidat> candidat = candidatService.getCandidatById(candidatId);
        // Optional: Use the candidatId if needed
        if (candidat != null) {
            model.addAttribute("candidat", candidat);
        }

        model.addAttribute("resultats", resultats);
        return "OffrespourCandidat";
    }

    @PostMapping("/postuleraemploie")
    public String postuleraemploie(@RequestParam(name = "candidatId") Long candidatId,
                                   @RequestParam(name = "offreId") Long offreId,
                                   Model model) {
        Optional<Candidat> candidatOpt = candidatService.getCandidatById(candidatId);
        Optional<OffreEmploi> offreOpt = offreEmploiService.getOffreById(offreId);

        if (candidatOpt.isPresent() && offreOpt.isPresent()) {
            Candidat candidat = candidatOpt.get();
            OffreEmploi offreEmploi = offreOpt.get();

            Optional<ResponsableRH> responsableRH = responsableRHService.getResponsableById(1L);

            Candidature candidature = new Candidature();
            candidature.setCandidat(candidat);
            candidature.setOffreEmploi(offreEmploi);
            candidature.setDateSoumission(new Date());
            candidature.setStatutCandidature("en cours");
            candidature.setResponsableRH(responsableRH.orElse(null)); // orElse(null) to avoid NullPointerException

            candidatureService.createCandidature(candidature);

            model.addAttribute("message", "Votre candidature a été envoyée avec succès.");
            return "MesCondidatuespourCandidat";
        } else {
            model.addAttribute("error", "Erreur: Candidat ou Offre d'emploi non trouvée.");
            return "erreur";
        }
    }


    @GetMapping("/detailmacandidature/{id}")
    public String detailmacandidature(@PathVariable Long id, Model model) {
        Candidature candidature = candidatureService.getCandidatureById(id).orElse(null);
        if (candidature != null) {
            Candidat candidat = candidature.getCandidat();
            model.addAttribute("candidature", candidature);
            model.addAttribute("candidat", candidat);
            return "CandidatureDetails";
        } else {
            return "redirect:/GestionCandidatures";
        }
    }



}




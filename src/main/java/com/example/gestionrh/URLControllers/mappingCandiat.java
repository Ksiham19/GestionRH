package com.example.gestionrh.URLControllers;

import com.example.gestionrh.Entities.*;
import com.example.gestionrh.Services.*;
import org.springframework.http.ResponseEntity;
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
    NotifiacationService notifiacationService;

    public mappingCandiat(ResponsableRHService responsableRHService,
                          NotifiacationService notifiacationService,CandidatService candidatService, OffreEmploiService offreEmploiService, CandidatureService candidatureService) {
        this.candidatService = candidatService;
        this.offreEmploiService = offreEmploiService;
        this.candidatureService = candidatureService;
        this.responsableRHService = responsableRHService;
        this.notifiacationService=notifiacationService;
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
                model.addAttribute("candidat", candidat);
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
        Optional<Candidat> candidat = candidatService.getCandidatById(candidatId);
        if (candidat != null) {
            model.addAttribute("candidat", candidat);
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
            List<Candidature> candidatures = candidat.getCandidatures();
            model.addAttribute("candidatures", candidatures);
            model.addAttribute("candidat", candidat);
            return "MesCondidatuespourCandidat";
        } else {
            model.addAttribute("error", "Erreur: Candidat ou Offre d'emploi non trouvée.");
            return "erreur";
        }
    }

    @GetMapping("/annuler/{id}")
    public String annulerCandidature(@PathVariable Long id,
                                     @RequestParam(name = "candidatId") Long candidatId,
                                     Model model) {
        boolean isDeleted = candidatureService.annulerCandidature(id);
        if (isDeleted) {
            Optional<Candidat> candidatOpt = candidatService.getCandidatById(candidatId);
            if (candidatOpt.isPresent()) {
                Candidat candidat = candidatOpt.get();
                List<Candidature> candidatures = candidat.getCandidatures();
                model.addAttribute("candidatures", candidatures);
                model.addAttribute("candidat", candidat);
                return "MesCondidatuespourCandidat"; // Assurez-vous que le nom de la vue est correct
            }
        }
        model.addAttribute("errorMessage", "Erreur lors de l'annulation de la candidature");
        return "error"; // Assurez-vous qu'il y a une vue error pour afficher les messages d'erreur
    }

    // Pour afficher les détails de la notification
    @GetMapping("/notification/{id}")
    public String viewNotificationDetails(@PathVariable Long id, Model model) {
        Optional<Notification> notification = notifiacationService.getNotificationById(id);
        if (notification.isPresent()) {
            model.addAttribute("notification", notification.get());
            return "notificationDetails"; // Nom de la vue HTML pour afficher les détails
        } else {
            model.addAttribute("error", "Notification non trouvée.");
            return "error"; // Rediriger vers une page d'erreur si la notification n'existe pas
        }
    }


    @PostMapping("/notification/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteNotification(@RequestParam Long id) {
        notifiacationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
// Redirigez vers une liste des notifications après suppression
    }






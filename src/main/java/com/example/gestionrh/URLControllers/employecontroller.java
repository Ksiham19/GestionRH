package com.example.gestionrh.URLControllers;

import com.example.gestionrh.Entities.BulletinPaie;
import com.example.gestionrh.Entities.DemandeBulletinPaie;
import com.example.gestionrh.Entities.DemandeDeConge;
import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Services.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/employe")
public class employecontroller {
    private final EmployeService employeService;
    private final DemandeDeCongeService demandeDeCongeService;
    private final DemandeBulletinPaieService demandeBulletinPaieService;
    private final BulletinPaieService bulletinPaieService;
    private final NotifiacationService notificationService;

    public employecontroller(EmployeService employeService,
                             DemandeBulletinPaieService demandeBulletinPaieService,
                             DemandeDeCongeService demandeDeCongeService,
                             BulletinPaieService bulletinPaieService,
                             NotifiacationService notificationService) {
        this.employeService = employeService;
        this.demandeDeCongeService = demandeDeCongeService;
        this.demandeBulletinPaieService=demandeBulletinPaieService;
        this.bulletinPaieService=bulletinPaieService;
        this.notificationService = notificationService;
    }

    @GetMapping("/deconnecter")
    public String deconnecter() {
        return "index";
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, Object> request) {
        Long employeId = Long.parseLong(request.get("employeId").toString());
        String currentPassword = request.get("currentPassword").toString();
        String newPassword = request.get("newPassword").toString();

        Map<String, Object> response = new HashMap<>();
        boolean success = employeService.changePassword(employeId, currentPassword, newPassword);

        if (success) {
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Échec du changement de mot de passe. Veuillez réessayer.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/DemandeConge")
    public String DemandeConge(@RequestParam("id") Long id, Model model) {
        Optional<Employe> employe = employeService.getEmployeById(id);
        if (employe.isEmpty()) {
            throw new RuntimeException("Employé introuvable avec l'ID : " + id);
        }
        model.addAttribute("employee", employe.get());
        return "DemandeConge";
    }

    @PostMapping("/envoyer")
    public String envoyerDemande(@RequestParam("employeeIdd") Long idEmploye,
                                 @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
                                 @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,
                                 @RequestParam("reason") String reason) {

        Employe employe = employeService.findById(idEmploye);

        // Créer une nouvelle demande de congé
        DemandeDeConge demandeDeConge = new DemandeDeConge();
        demandeDeConge.setEmploye(employe);
        demandeDeConge.setDateDebut(dateDebut);
        demandeDeConge.setDateFin(dateFin); // Ajoutez le motif ici
        demandeDeConge.setStatut("En attente"); // Par exemple, "En attente" par défaut

        // Sauvegarder la demande de congé dans la base de données
        demandeDeCongeService.createDemande(demandeDeConge);

        // Retourner une vue (ou rediriger)
        return "redirect:/success"; // Vous pouvez rediriger vers une page de succès ou afficher un message
    }

    @GetMapping("/DemandeBulletinPaie")
    public String DemandeBulletinPaie(@RequestParam("id") Long id, Model model) {
        Optional<Employe> employe = employeService.getEmployeById(id);
        if (employe.isEmpty()) {
            throw new RuntimeException("Employé introuvable avec l'ID : " + id);
        }
        model.addAttribute("employee", employe.get());
        return "DemandeBulletinPaie";
    }

    @PostMapping("/demander-bulletin")
    public String demanderBulletinPaie(@RequestParam("employeeId") Long employeeId) {
        Optional<Employe> employeOpt = employeService.getEmployeById(employeeId);
        if (employeOpt.isEmpty()) {
            throw new RuntimeException("Employé introuvable avec l'ID : " + employeeId);
        }
        Employe employe = employeOpt.get();

        DemandeBulletinPaie demande = new DemandeBulletinPaie();
        demande.setEmploye(employe);
        demande.setStatut("En attente");

        demandeBulletinPaieService.save(demande);

        // Vous pouvez ajouter ici le code pour notifier le responsable

        return "demande-soumise";
    }

    @PostMapping("/valider-bulletin")
    public String validerBulletin(@RequestParam("demandeId") Long demandeId,
                                  @RequestParam("montant") Double montant) {
        Optional<DemandeBulletinPaie> demandeOpt = demandeBulletinPaieService.findById(demandeId);
        if (demandeOpt.isEmpty()) {
            throw new RuntimeException("Demande introuvable avec l'ID : " + demandeId);
        }
        DemandeBulletinPaie demande = demandeOpt.get();

        BulletinPaie bulletin = new BulletinPaie();
        bulletin.setEmploye(demande.getEmploye());
        bulletin.setMoisAnnee("Mois/Année"); // Définir le mois et l'année appropriés
        bulletin.setMontant(montant);
        bulletin.setDateEmission(new Date());

        bulletinPaieService.createBulletin(bulletin);

        demande.setStatut("Complété");
        demandeBulletinPaieService.save(demande);

        // Envoyer une notification à l'employé
        String messageContenu = "Votre bulletin de paie pour le mois/année a été généré. Veuillez le télécharger depuis votre espace employé.";
        notificationService.envoyerNotificationEmploye(demande.getEmploye(), messageContenu);

        return "redirect:/responsable/demandes";
    }


}

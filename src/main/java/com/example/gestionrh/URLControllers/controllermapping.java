package com.example.gestionrh.URLControllers;

import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Entities.Candidature;
import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Entities.ResponsableRH;
import com.example.gestionrh.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/page1")
public class controllermapping {
    CandidatService candidatService;
    EmployeService employeService;
    ResponsableRHService responsableRHService;
    CandidatureService candidatureService;
    DemandeDeCongeService demandeDeCongeService;

    public controllermapping(CandidatService candidatService, EmployeService employeService, ResponsableRHService responsableRHService ,CandidatureService candidatureService,DemandeDeCongeService demandeDeCongeService) {
        this.candidatService=candidatService;
        this.employeService=employeService;
        this.responsableRHService=responsableRHService;
        this.candidatureService=candidatureService;
        this.demandeDeCongeService=demandeDeCongeService;
    }



    @GetMapping("/login")
    public String login() {
        return "login.html";
    }


    @PostMapping("/EspaceDedie")
    public String processLoginForm(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (responsableRHService.authenticate(username, password)) {
            long nombreCandidatures = candidatureService.countCandidature();
            long nombreEmploye = employeService.countmploye();
            long nombreDemande = demandeDeCongeService.countDemande();
            model.addAttribute("nombreCandidatures", nombreCandidatures);
            model.addAttribute("nombreEmploye", nombreEmploye);
            model.addAttribute("nombreDemande", nombreDemande);
            return "EspaceResponsable";
        } else if (employeService.authenticate(username, password)) {
            Optional<Employe> employe = employeService.getEmployeById(employeService.getEmployeIdByEmail(username));
            if (employe.isPresent()) {
                Employe employee = employe.get();
                model.addAttribute("employee", employee);
                return "EspaceEmploye";
            }
        } else if (candidatService.authenticate(username, password)) {
            Optional<Candidat> candidat = candidatService.getCandidatById(candidatService.getCandidatIdByEmail(username));
            if (candidat.isPresent()) {
                Candidat candidatt = candidat.get();
                model.addAttribute("candidatt", candidatt);
                return "EspaceCandidat";
            }
        } else {
            model.addAttribute("errorMessage", "L'utilisateur n'existe pas ou les informations d'identification sont incorrectes.");
            return "redirect:/login?error=true"; // Rediriger vers la page de login avec le paramètre d'erreur
        }
        return "redirect:/login?error=true";
    }


    @GetMapping("/dashbord")
    public String dashbord() {
        return "EspaceResponsable";
    }
    @GetMapping("/deconnecter")
    public String deconnecter() {
        return "index";
    }

    @GetMapping("/profiladmin")
    public String profiladmin(Model model) {
        Long id = 1L;
        Optional<ResponsableRH> respoOptional = responsableRHService.getResponsableById(id);

        if (respoOptional.isPresent()) {
            ResponsableRH respo = respoOptional.get();
            model.addAttribute("respo", respo);
        } else {
            // Handle the case where the ResponsableRH is not found (e.g., redirect to an error page or show a message)
            model.addAttribute("error", "Responsable RH non trouvé.");
        }

        return "profiladmin";
    }

    @GetMapping("/GestionEmployees")
    public String viewEmployees(Model model, @RequestParam(value = "search", required = false) String search) {
        List<Employe> listeEmployes;
        if (search != null && !search.isEmpty()) {
            listeEmployes = employeService.getEmployesByNom(search);
        } else {
            listeEmployes = employeService.getAllEmployes();
        }
        model.addAttribute("listeEmployes", listeEmployes);
        return "GestionEmployees";
    }

    @GetMapping("/ajouterEmploye")
    public String afficherFormulaireAjoutEmploye(Model model) {
        model.addAttribute("employe", new Employe());
        return "ajouterEmploye"; // Cela redirige vers une nouvelle page pour ajouter un employé
    }


    @GetMapping("/modifier/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Employe> employe = employeService.getEmployeById(id);
        if (employe.isEmpty()) {
            throw new RuntimeException("Employé introuvable avec l'ID : " + id);
        }
        model.addAttribute("employe", employe.get());
        return "modifierEmploye"; // Nom du fichier HTML pour le formulaire de modification
    }


    @PostMapping("/modifier/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employe") Employe employe,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) throws IOException {

        // Vérifiez si un fichier est téléchargé
        if (!file.isEmpty()) {
            // Définir le chemin où l'image sera sauvegardée
            String uploadDir = "C:\\Users\\Hp\\IdeaProjects\\GestionRH\\src\\main\\resources\\static\\images\\";
            File directory = new File(uploadDir);

            // Créer le répertoire s'il n'existe pas
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Définir le chemin complet du fichier
            String filePath = uploadDir + file.getOriginalFilename();
            File dest = new File(filePath);

            // Sauvegarder l'image sur le système de fichiers
            file.transferTo(dest);

            // Enregistrer le nom de fichier de l'image
            employe.setNomImage(file.getOriginalFilename());
            // Enregistrer le chemin de l'image (en tant que tableau d'octets)
            employe.setImage(filePath.getBytes());
        }

        // Mettre à jour les informations de l'employé
        employeService.updateEmploye(id, employe);

        // Rediriger vers la page de gestion des employés
        return "redirect:/page1/GestionEmployees";
    }



    @GetMapping("/supprimerEmploye/{id}")
    public String supprimerEmploye(@PathVariable("id") Long id){
        employeService.deleteEmploye(id);
        return "redirect:/page1/GestionEmployees";
    }

    @PostMapping("/saveEmploye")
    public String ajouterEmploye(@ModelAttribute("employe") Employe employe,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) throws IOException {

        // Vérifiez si un fichier est téléchargé
        if (!file.isEmpty()) {
            employe.setImage(file.getBytes()); // Récupérer l'image sous forme de tableau d'octets
            employe.setNomImage(file.getOriginalFilename()); // Enregistrer le nom de fichier de l'image si nécessaire
        }

        // Appeler le service pour créer l'employé
        employeService.createEmploye(employe);

        // Redirection avec un message de succès
        redirectAttributes.addFlashAttribute("successMessage", "Employé ajouté avec succès!");

        return "redirect:/page1/GestionEmployees";
    }

    @GetMapping("/inscription")
    public String inscription(Model model) {
        model.addAttribute("candid", new Candidat());
        return "inscription.html";
    }


    @GetMapping("/afficherDetailEmploye/{id}")
    public String afficherDetailEmploye(@PathVariable("id") Long id, Model model) {
        Optional<Employe> employeOptional = employeService.getEmployeById(id);
        if (employeOptional.isPresent()) {
            Employe employe = employeOptional.get();
            model.addAttribute("employe", employe);
            return "detailEmploye"; // Le nom de la vue pour afficher les détails de l'employé
        } else {
            // Gérer le cas où l'employé n'est pas trouvé
            return "employeNotFound"; // Exemple : une vue séparée pour afficher l'employé non trouvé
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("email") String email, @RequestParam("cin") String cin,
                                @RequestParam("newPassword") String newPassword, Model model) {
        if (responsableRHService.resetPassword(email, cin, newPassword) ||
                employeService.resetPassword(email, cin, newPassword) ||
                candidatService.resetPassword(email, cin, newPassword)) {
            return "mdpchange";
        } else {
            model.addAttribute("error", "Les informations fournies sont incorrectes.");
            return "login";
        }
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public Map<String, Object> changePassword(@RequestBody Map<String, String> request) {
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");
        Map<String, Object> response = new HashMap<>();

        // Récupérer l'utilisateur actuel (id=1 pour cet exemple)
        Long id = 1L;
        Optional<ResponsableRH> respoOptional = responsableRHService.getResponsableById(id);

        if (respoOptional.isPresent()) {
            ResponsableRH respo = respoOptional.get();

            // Vérification du mot de passe actuel
            if (!respo.getMdp().equals(currentPassword)) {
                response.put("success", false);
                response.put("message", "Le mot de passe actuel est incorrect.");
                return response;
            }

            // Changement du mot de passe
            respo.setMdp(newPassword);
            responsableRHService.updateResponsable(id,respo);
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Responsable RH non trouvé.");
        }

        return response;
    }



    @GetMapping("/GestionCandidatures")
    public String GestionCandidatures(Model model) {
        List<Candidature> candidatures = candidatureService.getCandidaturesByStatut("en cours");
        model.addAttribute("candidatures", candidatures);
        return "GestionCandidatures";
    }

    @GetMapping("/GestionCandidatures/{id}")
    public String getCandidatureDetails(@PathVariable Long id, Model model) {
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
    @PostMapping("/ajouterCandidat")
    public String ajouterCandidat(
            @ModelAttribute("candid") Candidat candidat,
            @RequestParam("file") MultipartFile photoFile,
            @RequestParam("filecv") MultipartFile cvFile,
            @RequestParam("filelettreMotivation") MultipartFile lettreMotivationFile,
            RedirectAttributes redirectAttributes) throws IOException {

        if (!photoFile.isEmpty()) {
            candidat.setPhoto(photoFile.getBytes());
            candidat.setNomphoto(photoFile.getOriginalFilename());
        }

        if (!cvFile.isEmpty()) {
            candidat.setCv(cvFile.getBytes());
            candidat.setNomcv(cvFile.getOriginalFilename());
        }
        if (!lettreMotivationFile.isEmpty()) {
            candidat.setLettreMotivation(lettreMotivationFile.getBytes());
            candidat.setNomlettreMotivation(lettreMotivationFile.getOriginalFilename());
        }

        candidatService.createCandidat(candidat);

        redirectAttributes.addFlashAttribute("message", "Candidat ajouté avec succès!");
        return "EspaceCandidat";
    }

    @GetMapping("/accepterCandidature/{id}")
    public String accepterCandidature(@PathVariable Long id) {
        candidatureService.accepterCandidature(id);
        return "redirect:/page1/GestionCandidatures";
    }

    @GetMapping("/refuserCandidature/{id}")
    public String refuserCandidature(@PathVariable long id){
        candidatureService.refuserCandidature(id);
        return "redirect:/page1/GestionCandidatures";
    }

}


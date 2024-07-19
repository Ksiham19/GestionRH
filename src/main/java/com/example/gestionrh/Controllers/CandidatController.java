package com.example.gestionrh.Controllers;

import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Repositories.CandidatRepository;
import com.example.gestionrh.Services.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Base64;

@RestController
@RequestMapping("/api/candidats")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private CandidatRepository candidatRepository;

    @PostMapping("/submit")
    public String submitCandidat(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam("mdp") String mdp,
            @RequestParam("telephone") String telephone,
            @RequestParam("adresse") String adresse,
            @RequestParam("competences") String competences,
            @RequestParam("cin") String cin,
            @RequestParam("cv") MultipartFile cv,
            @RequestParam("lettreMotivation") MultipartFile lettreMotivation,
            @RequestParam("photo") MultipartFile photo) {

        Candidat candidat = new Candidat();
        candidat.setNom(nom);
        candidat.setPrenom(prenom);
        candidat.setEmail(email);
        candidat.setMdp(mdp);
        candidat.setCin(cin);
        candidat.setTelephone(telephone);
        candidat.setAdresse(adresse);
        candidat.setCompetences(competences);

        try {
            // Convert MultipartFile to base64 String for cv
            /*String cvBase64 = Base64.getEncoder().encodeToString(cv.getBytes());
            candidat.setCv(cvBase64);*/
            candidat.setLettreMotivation(cv.getBytes());

            // Convert MultipartFile to byte[] for lettreMotivation and photo
            candidat.setLettreMotivation(lettreMotivation.getBytes());
            candidat.setPhoto(photo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur ici
        }

        candidatService.createCandidat(candidat);
        return "redirect:/success";
    }

    @PostMapping("/create")
    public Candidat createCandidat(@RequestBody Candidat candidat) {
        return candidatService.createCandidat(candidat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidat> updateCandidat(@PathVariable Long id, @RequestBody Candidat candidatDetails) {
        return ResponseEntity.ok(candidatService.updateCandidat(id, candidatDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Candidat> getCandidatsByNom(@RequestParam String nom) {
        return candidatService.getCandidatsByNom(nom);
    }
    @GetMapping("/download/cv/{id}")
    public ResponseEntity<ByteArrayResource> downloadCv(@PathVariable Long id) {
        Candidat candidat = candidatRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidat not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + candidat.getNomcv())
                .body(new ByteArrayResource(candidat.getCv()));
    }

    @GetMapping("/download/lettreMotivation/{id}")
    public ResponseEntity<ByteArrayResource> downloadLettreMotivation(@PathVariable Long id) {
        Candidat candidat = candidatRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidat not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + candidat.getNomlettreMotivation())
                .body(new ByteArrayResource(candidat.getLettreMotivation()));
    }
}

package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.Candidature;
import com.example.gestionrh.Services.CandidatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {
    @Autowired
    private CandidatureService service;

    @GetMapping
    public List<Candidature> getAllCandidatures() {
        return service.getAllCandidatures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable Long id) {
        Optional<Candidature> candidature = service.getCandidatureById(id);
        return candidature.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Candidature createCandidature(@RequestBody Candidature candidature) {
        return service.createCandidature(candidature);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidature> updateCandidature(@PathVariable Long id, @RequestBody Candidature candidatureDetails) {
        return ResponseEntity.ok(service.updateCandidature(id, candidatureDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        service.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Candidature> getCandidaturesByStatut(@RequestParam String statut) {
        return service.getCandidaturesByStatut(statut);
    }
}

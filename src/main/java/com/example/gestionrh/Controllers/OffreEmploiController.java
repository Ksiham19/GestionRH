package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.OffreEmploi;
import com.example.gestionrh.Services.OffreEmploiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offres")
public class OffreEmploiController {
    @Autowired
    private OffreEmploiService service;

    @GetMapping
    public List<OffreEmploi> getAllOffres() {
        return service.getAllOffres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreEmploi> getOffreById(@PathVariable Long id) {
        Optional<OffreEmploi> offre = service.getOffreById(id);
        return offre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public OffreEmploi createOffre(@RequestBody OffreEmploi offre) {
        return service.createOffre(offre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OffreEmploi> updateOffre(@PathVariable Long id, @RequestBody OffreEmploi offreDetails) {
        return ResponseEntity.ok(service.updateOffre(id, offreDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        service.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<OffreEmploi> getOffresByTitre(@RequestParam String titre) {
        return service.getOffresByTitre(titre);
    }
}

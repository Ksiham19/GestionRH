package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.DemandeDeConge;
import com.example.gestionrh.Repositories.DemandeCongeRepository;
import com.example.gestionrh.Services.DemandeDeCongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/demandes")
public class DemandeDeCongeController {
    @Autowired
    private DemandeDeCongeService demandeCongeService;

    @GetMapping
    public ResponseEntity<List<DemandeDeConge>> getAllDemandesConge() {
        List<DemandeDeConge> demandesConge = demandeCongeService.getAllDemandes();
        return ResponseEntity.ok().body(demandesConge);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeDeConge> getDemandeCongeById(@PathVariable Long id) {
        return demandeCongeService.getDemandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DemandeDeConge> createDemandeConge(@RequestBody DemandeDeConge demandeConge) {
        DemandeDeConge createdDemandeConge = demandeCongeService.createDemande(demandeConge);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemandeConge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandeDeConge> updateDemandeConge(@PathVariable Long id, @RequestBody DemandeDeConge demandeConge) {
        if (!demandeCongeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        demandeConge.setIdDemande(id); // Ensure the ID is set for update
        demandeCongeService.updateDemande(id,demandeConge);
        return ResponseEntity.ok().body(demandeConge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandeConge(@PathVariable Long id) {
        if (!demandeCongeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        demandeCongeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }

    }



package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.Entretien;
import com.example.gestionrh.Services.EntretienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entretiens")
public class EntretienController {
    @Autowired
    private EntretienService entretienService;

    @GetMapping
    public ResponseEntity<List<Entretien>> getAllEntretiens() {
        List<Entretien> entretiens = entretienService.getAllEntretiens();
        return ResponseEntity.ok().body(entretiens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entretien> getEntretienById(@PathVariable Long id) {
        return entretienService.getEntretienById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entretien> createEntretien(@RequestBody Entretien entretien) {
        Entretien createdEntretien = entretienService.createEntretien(entretien);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntretien);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entretien> updateEntretien(@PathVariable Long id, @RequestBody Entretien entretien) {
        if (!entretienService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entretien.setIdEntretien(id); // Ensure the ID is set for update
        entretienService.updateEntretien(id,entretien);
        return ResponseEntity.ok().body(entretien);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntretien(@PathVariable Long id) {
        if (!entretienService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entretienService.deleteEntretien(id);
        return ResponseEntity.noContent().build();
    }
}

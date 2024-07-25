package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.Formation;
import com.example.gestionrh.Services.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/formations")
public class FormationController {
    @Autowired
    private FormationService service;

    @GetMapping
    public List<Formation> getAllFormations() {
        return service.getAllFormations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Optional<Formation> formation = service.getFormationById(id);
        return formation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        Formation createdFormation = service.createFormation(formation);
        return ResponseEntity.created(URI.create("/api/formations/" + createdFormation.getIdFormation()))
                .body(createdFormation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        formation.setIdFormation(id);
        service.updateFormation(formation);
        return ResponseEntity.ok(formation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) throws Exception {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteFormationById(id);
        return ResponseEntity.noContent().build();
    }
}

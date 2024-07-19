package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.ResponsableRH;
import com.example.gestionrh.Services.ResponsableRHService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/responsables")
public class ResponsableRHController {
    @Autowired
    private ResponsableRHService service;

    @GetMapping
    public List<ResponsableRH> getAllResponsables() {
        return service.getAllResponsables();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsableRH> getResponsableById(@PathVariable Long id) {
        Optional<ResponsableRH> responsable = service.getResponsableById(id);
        return responsable.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponsableRH createResponsable(@RequestBody ResponsableRH responsable) {
        return service.createResponsable(responsable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsableRH> updateResponsable(@PathVariable Long id, @RequestBody ResponsableRH responsableDetails) {
        return ResponseEntity.ok(service.updateResponsable(id, responsableDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        service.deleteResponsable(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<ResponsableRH> getResponsablesByNom(@RequestParam String nom) {
        return service.getResponsablesByNom(nom);
    }
}

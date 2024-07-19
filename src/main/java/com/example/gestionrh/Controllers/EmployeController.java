package com.example.gestionrh.Controllers;

import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeService service;

    @GetMapping
    public List<Employe> getAllEmployes() {
        return service.getAllEmployes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        Optional<Employe> employe = service.getEmployeById(id);
        return employe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employe createEmploye(@RequestBody Employe employe) {
        return service.createEmploye(employe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employeDetails) {
        return ResponseEntity.ok(service.updateEmploye(id, employeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        service.deleteEmploye(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Employe> getEmployesByNom(@RequestParam String nom) {
        return service.getEmployesByNom(nom);
    }
}

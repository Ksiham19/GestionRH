package com.example.gestionrh.Controllers;
import com.example.gestionrh.Entities.ParticipationFormation;
import com.example.gestionrh.Services.ParticipationFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Participationformations")
public class ParticipationForationController {
    @Autowired
    private ParticipationFormationService participationFormationService;

    @GetMapping
    public ResponseEntity<List<ParticipationFormation>> getAllParticipationsFormation() {
        List<ParticipationFormation> participationsFormation = participationFormationService.getAllParticipations();
        return ResponseEntity.ok().body(participationsFormation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipationFormation> getParticipationFormationById(@PathVariable Long id) {
        return participationFormationService.getParticipationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParticipationFormation> createParticipationFormation(@RequestBody ParticipationFormation participationFormation) {
        ParticipationFormation createdParticipationFormation = participationFormationService.createParticipation(participationFormation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipationFormation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipationFormation> updateParticipationFormation(@PathVariable Long id, @RequestBody ParticipationFormation participationFormation) {
        if (!participationFormationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        participationFormation.setIdParticipation(id); // Ensure the ID is set for update
        participationFormationService.updateParticipation(id,participationFormation);
        return ResponseEntity.ok().body(participationFormation);
    }
/*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipationFormation(@PathVariable Long id) {
        if (!participationFormationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        participationFormationService.deleteParticipation(id);
        return ResponseEntity.noContent().build();
    }

 */
}

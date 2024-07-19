package com.example.gestionrh.Controllers;

import com.example.gestionrh.Entities.BulletinPaie;
import com.example.gestionrh.Services.BulletinPaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulletinspaie")
public class BulletinPaieController {
    @Autowired
    private BulletinPaieService bulletinPaieService;

    @GetMapping
    public ResponseEntity<List<BulletinPaie>> getAllBulletinsPaie() {
        List<BulletinPaie> bulletinsPaie = bulletinPaieService.getAllBulletins();
        return ResponseEntity.ok().body(bulletinsPaie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BulletinPaie> getBulletinPaieById(@PathVariable Long id) {
        return bulletinPaieService.getBulletinById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BulletinPaie> createBulletinPaie(@RequestBody BulletinPaie bulletinPaie) {
        BulletinPaie createdBulletinPaie = bulletinPaieService.createBulletin(bulletinPaie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBulletinPaie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BulletinPaie> updateBulletinPaie(@PathVariable Long id, @RequestBody BulletinPaie bulletinPaie) {
        if (!bulletinPaieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bulletinPaie.setIdBulletin(id); // Ensure the ID is set for update
        bulletinPaieService.updateBulletin(id,bulletinPaie);
        return ResponseEntity.ok().body(bulletinPaie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBulletinPaie(@PathVariable Long id) {
        if (!bulletinPaieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bulletinPaieService.deleteBulletin(id);
        return ResponseEntity.noContent().build();
    }
}

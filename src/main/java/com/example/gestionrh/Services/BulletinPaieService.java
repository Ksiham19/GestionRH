package com.example.gestionrh.Services;

import com.example.gestionrh.Entities.BulletinPaie;
import com.example.gestionrh.Repositories.BulletinPaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BulletinPaieService {
    @Autowired
    private BulletinPaieRepository repository;

    public List<BulletinPaie> getAllBulletins() {
        return repository.findAll();
    }

    public Optional<BulletinPaie> getBulletinById(Long id) {
        return repository.findById(id);
    }

    public BulletinPaie createBulletin(BulletinPaie bulletin) {
        return repository.save(bulletin);
    }

    public BulletinPaie updateBulletin(Long id, BulletinPaie bulletinDetails) {
        BulletinPaie bulletin = repository.findById(id).orElseThrow(() -> new RuntimeException("Bulletin not found"));
        bulletin.setMoisAnnee(bulletinDetails.getMoisAnnee());
        bulletin.setMontant(bulletinDetails.getMontant());
        bulletin.setDateEmission(bulletinDetails.getDateEmission());
        return repository.save(bulletin);
    }

    public void deleteBulletin(Long id) {
        repository.deleteById(id);
    }

    public List<BulletinPaie> getBulletinsByMoisAnnee(String moisAnnee) {
        return repository.findByMoisAnnee(moisAnnee);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}

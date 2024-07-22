package com.example.gestionrh.Services;

import com.example.gestionrh.Entities.DemandeBulletinPaie;
import com.example.gestionrh.Repositories.DemandeBulletinPaieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemandeBulletinPaieService {
    private final DemandeBulletinPaieRepository repository;

    public DemandeBulletinPaieService(DemandeBulletinPaieRepository repository) {
        this.repository = repository;
    }

    public void save(DemandeBulletinPaie demande) {
        repository.save(demande);
    }

    public Optional<DemandeBulletinPaie> findById(Long id) {
        return repository.findById(id);
    }
}

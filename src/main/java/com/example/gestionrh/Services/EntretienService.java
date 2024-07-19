package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Entretien;
import com.example.gestionrh.Repositories.EntretienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntretienService {
    @Autowired
    private EntretienRepository repository;

    public List<Entretien> getAllEntretiens() {
        return repository.findAll();
    }

    public Optional<Entretien> getEntretienById(Long id) {
        return repository.findById(id);
    }

    public Entretien createEntretien(Entretien entretien) {
        return repository.save(entretien);
    }

    public Entretien updateEntretien(Long id, Entretien entretienDetails) {
        Entretien entretien = repository.findById(id).orElseThrow(() -> new RuntimeException("Entretien not found"));
        entretien.setDateEntretien(entretienDetails.getDateEntretien());
        entretien.setLieu(entretienDetails.getLieu());
        entretien.setDescription(entretienDetails.getDescription());
        return repository.save(entretien);
    }

    public void deleteEntretien(Long id) {
        repository.deleteById(id);
    }
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}

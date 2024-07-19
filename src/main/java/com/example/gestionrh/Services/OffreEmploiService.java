package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.OffreEmploi;
import com.example.gestionrh.Repositories.OffreEmploiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreEmploiService {
    @Autowired
    private OffreEmploiRepository repository;

    public List<OffreEmploi> getAllOffres() {
        return repository.findAll();
    }

    public Optional<OffreEmploi> getOffreById(Long id) {
        return repository.findById(id);
    }

    public OffreEmploi createOffre(OffreEmploi offre) {
        return repository.save(offre);
    }

    public OffreEmploi updateOffre(Long id, OffreEmploi offreDetails) {
        OffreEmploi offre = repository.findById(id).orElseThrow(() -> new RuntimeException("Offre not found"));
        offre.setTitre(offreDetails.getTitre());
        offre.setDescription(offreDetails.getDescription());
        offre.setCompetencesRequises(offreDetails.getCompetencesRequises());
        offre.setDatePublication(offreDetails.getDatePublication());
        offre.setStatut(offreDetails.getStatut());
        return repository.save(offre);
    }

    public void deleteOffre(Long id) {
        repository.deleteById(id);
    }

    public List<OffreEmploi> getOffresByTitre(String titre) {
        return repository.findByTitre(titre);
    }
    public List<OffreEmploi> getOffresByType(String type) {
        return repository.findByType(type);
    }
}
